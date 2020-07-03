package com.example.currencyconverter.dagger

import com.example.currencyconverter.data.repository.CurrencyRepository
import com.example.currencyconverter.data.repository.CurrencyRepositoryImpl
import dagger.Module
import dagger.Provides

@Module
class RepositoryModule {

    @Provides
    fun provideCurrencyRepository(repository: CurrencyRepositoryImpl): CurrencyRepository = repository

}