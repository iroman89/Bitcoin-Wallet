package com.beetrack.bitcoinwallet.presentation.di.module

import androidx.lifecycle.ViewModelProvider
import com.beetrack.bitcoinwallet.presentation.util.ViewModelFactory
import dagger.Binds
import dagger.Module

@Module
abstract class ViewModelModule {

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}