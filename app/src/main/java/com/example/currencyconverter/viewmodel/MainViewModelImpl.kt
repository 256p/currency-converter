package com.example.currencyconverter.viewmodel

import androidx.lifecycle.ViewModel
import com.example.currencyconverter.data.model.Currency
import com.example.currencyconverter.interactor.MainInteractor
import com.example.currencyconverter.util.SchedulerProvider
import com.revolut.rxdata.core.Data
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject
import java.util.*
import javax.inject.Inject

class MainViewModelImpl @Inject constructor(
    private val interactor: MainInteractor,
    private val schedulerProvider: SchedulerProvider
) : ViewModel(), MainViewModel {

    private val disposable = CompositeDisposable()
    private val currencyRatesSubject = PublishSubject.create<Data<Currency>>()
    private val timer = Timer()
    private var baseCurrency = "EUR"

    init {
        timer.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                requestCurrencyRates(baseCurrency, true)
            }
        }, 0, 1000)
    }

    override fun getCurrencyRatesObservable(): Observable<Data<Currency>> =
        currencyRatesSubject
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())

    override fun requestCurrencyRates(base: String, forceReload: Boolean) {
        baseCurrency = base
        disposable.add(interactor.getCurrencyRates(base, forceReload)
            .subscribeOn(schedulerProvider.io())
            .subscribe {
                currencyRatesSubject.onNext(it)
            }
        )
    }

    override fun onCleared() {
        super.onCleared()
        if (!disposable.isDisposed) disposable.dispose()
        timer.cancel()
    }
}