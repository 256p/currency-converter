package com.example.currencyconverter.data.repository

import com.example.currencyconverter.data.network.ApiService
import com.example.currencyconverter.data.network.model.Currency
import com.revolut.rxdata.core.Data
import com.revolut.rxdata.dod.DataObservableDelegate
import io.reactivex.Observable
import javax.inject.Inject

class CurrencyRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : CurrencyRepository {
    
    override fun observeCurrency(forceReload: Boolean): Observable<Data<Currency>> {
        TODO("Not yet implemented")
    }

}