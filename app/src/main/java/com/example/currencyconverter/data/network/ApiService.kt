package com.example.currencyconverter.data.network

import com.example.currencyconverter.data.network.model.Currency
import io.reactivex.Single
import javax.inject.Inject

class ApiService @Inject constructor(
    private val apiInterface: ApiInterface
) {

    fun getCurrencyRates(base: String): Single<Currency> = apiInterface.getCurrencyRates(base)

}