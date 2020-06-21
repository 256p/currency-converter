package com.example.currencyconverter.dagger

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.currencyconverter.viewmodel.MainViewModelImpl
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModelImpl::class)
    internal abstract fun bindMainViewModel(viewModel: MainViewModelImpl): ViewModel

}