package com.example.currencyconverter.viewmodel

import com.example.currencyconverter.data.model.Currency
import com.revolut.rxdata.core.Data
import io.reactivex.Observable

interface MainViewModel {

    fun setBaseCurrency(baseCurrency: String)
    fun getCurrencyRatesObservable() : Observable<Data<Currency>>
    fun requestCurrencyRatesImmediately(base: String, forceReload: Boolean)
    fun cancelUpdateRequests()
    fun startUpdateRequests()
}