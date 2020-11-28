package com.beetrack.bitcoinwallet.presentation.di.component

import android.content.Context
import com.beetrack.bitcoinwallet.presentation.BitcoinWalletApplication
import com.beetrack.bitcoinwallet.presentation.di.module.DatabaseModule
import com.beetrack.bitcoinwallet.presentation.di.module.RepositoryModule
import com.beetrack.bitcoinwallet.presentation.di.module.RetrofitModule
import com.beetrack.bitcoinwallet.presentation.di.module.ViewModelModule
import com.beetrack.bitcoinwallet.presentation.ui.address.AddressGenerationFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        RetrofitModule::class,
        RepositoryModule::class,
        ViewModelModule::class,
        DatabaseModule::class
    ]
)
interface ApplicationComponent {

    @Component.Builder
    interface Builder {
        fun build(): ApplicationComponent

        @BindsInstance
        fun context(context: Context): Builder
    }

    fun inject(bitcoinWalletApplication: BitcoinWalletApplication)
    fun inject(addressGenerationFragment: AddressGenerationFragment)
}
