package com.example.currencyconverter.util

import io.reactivex.Scheduler

interface SchedulerProvider {

    fun io() : Scheduler
    fun ui() : Scheduler

}