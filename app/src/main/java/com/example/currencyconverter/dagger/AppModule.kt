package com.example.currencyconverter.dagger

import android.content.Context
import androidx.room.Room
import com.example.currencyconverter.data.db.AppDatabase
import com.example.currencyconverter.data.network.ApiInterface
import com.example.currencyconverter.data.network.ApiService
import com.example.currencyconverter.util.SchedulerProvider
import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.ConnectionSpec
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.TlsVersion
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Singleton

@Module
public class AppModule {

    @Provides
    @Singleton
    fun providesLoggingInterceptor(): Interceptor {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return loggingInterceptor
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        loggingInterceptor: Interceptor
    ): OkHttpClient {
        val strict = ConnectionSpec.Builder(ConnectionSpec.MODERN_TLS)
            .tlsVersions(TlsVersion.TLS_1_2, TlsVersion.TLS_1_3)
            .build()
        val connectionSpecs = listOf(strict)

        return OkHttpClient.Builder()
            .connectionSpecs(connectionSpecs)
            .addInterceptor(loggingInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(apiInterface: ApiInterface): ApiService = ApiService(apiInterface)

    @Provides
    @Singleton
    fun provideSchedulerProvider(): SchedulerProvider = object : SchedulerProvider {
        override fun io(): Scheduler = Schedulers.io()
        override fun ui(): Scheduler = AndroidSchedulers.mainThread()
    }

    @Provides
    @Singleton
    fun provideDatabase(context: Context) : AppDatabase = Room.databaseBuilder(
        context,
        AppDatabase::class.java,
        "currency-database"
    ).build()

}