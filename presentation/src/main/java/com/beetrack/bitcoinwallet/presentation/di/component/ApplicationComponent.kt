package com.beetrack.bitcoinwallet.presentation.di.component

import com.beetrack.bitcoinwallet.presentation.di.module.DatabaseModule
import com.beetrack.bitcoinwallet.presentation.di.module.RepositoryModule
import com.beetrack.bitcoinwallet.presentation.di.module.RetrofitModule
import com.beetrack.bitcoinwallet.presentation.di.module.ViewModelModule
import com.beetrack.bitcoinwallet.presentation.ui.adress.AddressGenerationFragment
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
    fun inject(addressGenerationFragment: AddressGenerationFragment)
}
