package com.example.currencyconverter.data.db.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.currencyconverter.data.model.Currency
import com.example.currencyconverter.data.model.Rates

@Entity
data class CurrencyEntity(
    @PrimaryKey val uid: Int,
    val baseCurrency: String,
    @Embedded val rates: RatesEmbedded
) {

    fun toDomain() = Currency(baseCurrency, rates.toDomain())
}

data class RatesEmbedded(
    val EUR: Double?,
    val AUD: Double?,
    val BGN: Double?,
    val BRL: Double?,
    val CAD: Double?,
    val CHF: Double?,
    val CNY: Double?,
    val CZK: Double?,
    val DKK: Double?,
    val GBP: Double?,
    val HKD: Double?,
    val HRK: Double?,
    val HUF: Double?,
    val IDR: Double?,
    val ILS: Double?,
    val INR: Double?,
    val ISK: Double?,
    val JPY: Double?,
    val KRW: Double?,
    val MXN: Double?,
    val MYR: Double?,
    val NOK: Double?,
    val NZD: Double?,
    val PHP: Double?,
    val PLN: Double?,
    val RON: Double?,
    val RUB: Double?,
    val SEK: Double?,
    val SGD: Double?,
    val THB: Double?,
    val USD: Double?,
    val ZAR: Double?
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