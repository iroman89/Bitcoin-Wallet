package com.beetrack.bitcoinwallet.presentation.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.beetrack.bitcoinwallet.presentation.ui.adress.AddressGenerationViewModel
import com.beetrack.bitcoinwallet.presentation.util.ViewModelFactory
import com.beetrack.bitcoinwallet.presentation.util.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @IntoMap
    @ViewModelKey(AddressGenerationViewModel::class)
    @Binds
    abstract fun bindAddressGeneratorViewModel(viewModel: AddressGenerationViewModel): ViewModel
}