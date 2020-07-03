package com.example.currencyconverter

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner


class UiTestsRunner : AndroidJUnitRunner() {
    override fun newApplication(
        cl: ClassLoader?,
        className: String?,
        context: Context?
    ): Application {
        return super.newApplication(cl, TestApp::class.java.name, context)
    }
}