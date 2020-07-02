package com.example.currencyconverter.dagger

import android.content.Context
import com.example.currencyconverter.TestApp
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AppModule::class,
    TestApiModule::class,
    InteractorModule::class,
    RepositoryModule::class,
    ViewModelModule::class,
    ActivityBuilder::class,
    AndroidInjectionModule::class
])
interface TestAppComponent {

    fun inject(testApp: TestApp)

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): TestAppComponent
    }
}