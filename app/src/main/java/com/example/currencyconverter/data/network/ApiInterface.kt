package com.example.currencyconverter.data.network

import com.example.currencyconverter.data.network.dto.CurrencyDto
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {

    @GET("latest")
    fun getCurrencyRates(@Query("base") base: String): Single<CurrencyDto>

}