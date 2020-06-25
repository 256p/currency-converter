package com.example.currencyconverter.data.repository

import com.example.currencyconverter.data.db.AppDatabase
import com.example.currencyconverter.data.model.Currency
import com.example.currencyconverter.data.network.ApiService
import com.revolut.rxdata.core.Data
import com.revolut.rxdata.dod.DataObservableDelegate
import io.reactivex.Observable
import javax.inject.Inject

class CurrencyRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val appDatabase: AppDatabase
) : CurrencyRepository {

    private val currencyCache = mutableMapOf<String, Currency>()
    private val currencyDao = appDatabase.currencyDao()

    private val currencyDod: DataObservableDelegate<String, Currency> = DataObservableDelegate(
        fromNetwork = { apiService.getCurrencyRates(it).map { currencyDto -> currencyDto.toDomain() } },
        fromMemory = { currencyCache[it] },
        fromStorage = { currencyDao.getCurrency()?.toDomain() },
        toMemory = { key, currency -> currencyCache[key] = currency },
        toStorage = { _, currency -> currencyDao.saveCurrency(currency.toDb()) }
    )

    override fun observeCurrencyRates(base: String, forceReload: Boolean): Observable<Data<Currency>> =
        currencyDod.observe(base, forceReload)

}