package com.example.currencyconverter.data.network.dto

import com.example.currencyconverter.data.model.Currency
import com.example.currencyconverter.data.model.Rates

data class CurrencyDto(
    val baseCurrency: String,
    val rates: RatesDto
) {

    fun toDomain() = Currency(baseCurrency, rates.toDomain())
}

data class RatesDto(
    val EUR: Float?,
    val AUD: Float?,
    val BGN: Float?,
    val BRL: Float?,
    val CAD: Float?,
    val CHF: Float?,
    val CNY: Float?,
    val CZK: Float?,
    val DKK: Float?,
    val GBP: Float?,
    val HKD: Float?,
    val HRK: Float?,
    val HUF: Float?,
    val IDR: Float?,
    val ILS: Float?,
    val INR: Float?,
    val ISK: Float?,
    val JPY: Float?,
    val KRW: Float?,
    val MXN: Float?,
    val MYR: Float?,
    val NOK: Float?,
    val NZD: Float?,
    val PHP: Float?,
    val PLN: Float?,
    val RON: Float?,
    val RUB: Float?,
    val SEK: Float?,
    val SGD: Float?,
    val THB: Float?,
    val USD: Float?,
    val ZAR: Float?
) {

    fun toDomain() = Rates(
        EUR,
        AUD,
        BGN,
        BRL,
        CAD,
        CHF,
        CNY,
        CZK,
        DKK,
        GBP,
        HKD,
        HRK,
        HUF,
        IDR,
        ILS,
        INR,
        ISK,
        JPY,
        KRW,
        MXN,
        MYR,
        NOK,
        NZD,
        PHP,
        PLN,
        RON,
        RUB,
        SEK,
        SGD,
        THB,
        USD,
        ZAR
    )
}