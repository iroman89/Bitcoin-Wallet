package cl.iroman.bitcoinwallet.presentation.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import cl.iroman.bitcoinwallet.presentation.ui.address.generator.viewModel.AddressViewModel
import cl.iroman.bitcoinwallet.presentation.ui.address.historyTransaction.viewModel.HistoryTransactionViewModel
import cl.iroman.bitcoinwallet.presentation.ui.address.balance.viewModel.BalanceViewModel
import cl.iroman.bitcoinwallet.presentation.util.ViewModelFactory
import cl.iroman.bitcoinwallet.presentation.util.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(AddressViewModel::class)
    abstract fun bindAddressGeneratorViewModel(viewModel: AddressViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(BalanceViewModel::class)
    abstract fun bindStateViewModel(viewModel: BalanceViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(HistoryTransactionViewModel::class)
    abstract fun bindHistoryViewModel(viewModel: HistoryTransactionViewModel): ViewModel
}