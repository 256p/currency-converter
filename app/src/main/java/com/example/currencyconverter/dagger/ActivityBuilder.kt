package com.example.currencyconverter.dagger

import com.example.currencyconverter.ui.activity.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilder {
    @ContributesAndroidInjector
    abstract fun contributeMainActivityInjector(): MainActivity
}