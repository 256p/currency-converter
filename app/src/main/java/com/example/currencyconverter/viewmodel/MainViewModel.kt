package com.example.currencyconverter.viewmodel

import com.example.currencyconverter.data.model.Currency
import com.revolut.rxdata.core.Data
import io.reactivex.Observable
import io.reactivex.Single

interface MainViewModel {

    fun getCurrencyRatesObservable() : Observable<Data<Currency>>
    fun requestCurrencyRates(base: String, forceReload: Boolean)
}