package com.example.currencyconverter.viewmodel

import androidx.lifecycle.ViewModel
import com.example.currencyconverter.interactor.MainInteractor
import com.example.currencyconverter.util.SchedulerProvider
import javax.inject.Inject

class MainViewModelImpl @Inject constructor(
    private val interactor: MainInteractor,
    private val schedulerProvider: SchedulerProvider
) : ViewModel(), MainViewModel {



}