package com.example.currencyconverter.interactor

import com.example.currencyconverter.data.model.Currency
import com.example.currencyconverter.data.repository.CurrencyRepository
import com.revolut.rxdata.core.Data
import io.reactivex.Observable
import javax.inject.Inject

class MainInteractorImpl @Inject constructor(
    private val currencyRepository: CurrencyRepository
) : BaseInteractorImpl(), MainInteractor {

    override fun getCurrencyRates(base: String, forceReload: Boolean): Observable<Data<Currency>> =
        currencyRepository.observeCurrencyRates(base, forceReload)

}