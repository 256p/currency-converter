package com.example.currencyconverter.data.repository

import com.example.currencyconverter.data.model.Currency
import com.revolut.rxdata.core.Data
import io.reactivex.Observable

interface CurrencyRepository {

    fun observeCurrencyRates(base: String, forceReload: Boolean = true): Observable<Data<Currency>>

}