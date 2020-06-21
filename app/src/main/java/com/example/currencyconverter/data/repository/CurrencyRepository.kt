package com.example.currencyconverter.data.repository

import com.example.currencyconverter.data.network.model.Currency
import com.revolut.rxdata.core.Data
import io.reactivex.Observable

interface CurrencyRepository {

    fun observeCurrency(forceReload: Boolean = true): Observable<Data<Currency>>

}