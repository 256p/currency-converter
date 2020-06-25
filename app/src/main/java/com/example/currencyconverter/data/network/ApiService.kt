package com.example.currencyconverter.data.network

import com.example.currencyconverter.data.network.dto.CurrencyDto
import io.reactivex.Single
import javax.inject.Inject

class ApiService @Inject constructor(
    private val apiInterface: ApiInterface
) {

    fun getCurrencyRates(base: String): Single<CurrencyDto> = apiInterface.getCurrencyRates(base)

}