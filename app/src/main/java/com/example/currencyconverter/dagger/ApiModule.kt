package com.example.currencyconverter.dagger

import com.example.currencyconverter.BuildConfig
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
public class ApiModule {

    @Provides
    @Singleton
    open fun provideApiInterface(client: OkHttpClient): ApiInterface {
        val gson = GsonBuilder()
            .setLenient()
            .create()

        val retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.API_BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()

        return retrofit.create(ApiInterface::class.java)
    }

}