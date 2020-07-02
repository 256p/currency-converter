package com.example.currencyconverter

import android.app.Application
import com.example.currencyconverter.dagger.DaggerTestAppComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

class TestApp : Application(), HasAndroidInjector {

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>

    override fun onCreate() {
        super.onCreate()
        DaggerTestAppComponent.factory().create(this).inject(this)
    }

    override fun androidInjector(): AndroidInjector<Any> = androidInjector

}