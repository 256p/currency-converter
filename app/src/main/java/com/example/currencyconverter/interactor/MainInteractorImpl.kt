package com.example.currencyconverter.interactor

import com.example.currencyconverter.data.repository.CurrencyRepository
import javax.inject.Inject

class MainInteractorImpl @Inject constructor(
    private val currencyRepository: CurrencyRepository
) : BaseInteractorImpl(), MainInteractor {

}