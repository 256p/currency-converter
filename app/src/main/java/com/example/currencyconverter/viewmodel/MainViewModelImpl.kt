package com.example.currencyconverter.viewmodel

import android.text.format.DateUtils
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
    private var timer = Timer()
    private var baseCurrency = "EUR"
    private var lastUpdateTimestamp: Long = 0

    override fun setBaseCurrency(baseCurrency: String) {
        this.baseCurrency = baseCurrency
    }

    override fun getCurrencyRatesObservable(): Observable<Data<Currency>> =
        currencyRatesSubject
            .filter {
                if (System.currentTimeMillis() - lastUpdateTimestamp >= DateUtils.SECOND_IN_MILLIS) {
                    lastUpdateTimestamp = System.currentTimeMillis()
                    return@filter true
                } else {
                    return@filter false
                }
            }
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())

    override fun requestCurrencyRatesImmediately(base: String, forceReload: Boolean) {
        baseCurrency = base
        lastUpdateTimestamp = 0
        disposable.add(interactor.getCurrencyRates(base, forceReload)
            .subscribeOn(schedulerProvider.io())
            .subscribe {
                currencyRatesSubject.onNext(it)
            }
        )
    }

    override fun cancelUpdateRequests() {
        timer.cancel()
        timer.purge()
    }

    override fun startUpdateRequests() {
        timer.cancel()
        timer.purge()
        timer = Timer()
        timer.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                requestCurrencyRatesImmediately(baseCurrency, true)
            }
        }, 0, DateUtils.SECOND_IN_MILLIS)
    }

    override fun onCleared() {
        super.onCleared()
        if (!disposable.isDisposed) disposable.dispose()
        timer.cancel()
        timer.purge()
    }
}