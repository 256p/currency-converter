package com.example.currencyconverter.dagger

import com.example.currencyconverter.interactor.MainInteractor
import com.example.currencyconverter.interactor.MainInteractorImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class InteractorModule {

    @Provides
    @Singleton
    fun provideMainInteractor(interactor: MainInteractorImpl): MainInteractor = interactor

}