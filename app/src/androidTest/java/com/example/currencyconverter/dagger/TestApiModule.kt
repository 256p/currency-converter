package com.example.currencyconverter.dagger

import com.example.currencyconverter.BuildConfig
import com.example.currencyconverter.MockApiInterceptor
import com.example.currencyconverter.data.network.ApiInterface
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
public class TestApiModule {

    @Provides
    @Singleton
    fun provideApiInterface(): ApiInterface {
        val gson = GsonBuilder()
            .setLenient()
            .create()

        val mockClient = OkHttpClient.Builder()
            .addInterceptor(MockApiInterceptor())
            .build()

        val mockRetrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.API_BASE_URL)
            .client(mockClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()

        return mockRetrofit.create(ApiInterface::class.java)
    }

}