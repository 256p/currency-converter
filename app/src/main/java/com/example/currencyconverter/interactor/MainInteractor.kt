package com.example.currencyconverter.interactor

import com.example.currencyconverter.data.model.Currency
import com.revolut.rxdata.core.Data
import io.reactivex.Observable

interface MainInteractor {

    fun getCurrencyRates(base: String, forceReload: Boolean) : Observable<Data<Currency>>

}