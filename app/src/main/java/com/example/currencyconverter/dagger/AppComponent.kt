package com.example.currencyconverter.dagger

import android.content.Context
import com.example.currencyconverter.App
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AppModule::class,
    InteractorModule::class,
    RepositoryModule::class,
    ViewModelModule::class,
    ActivityBuilder::class,
    AndroidInjectionModule::class
])
interface AppComponent {

    fun inject(app: App)

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }

}