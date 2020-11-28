package com.beetrack.bitcoinwallet.presentation

import android.app.Application
import com.beetrack.bitcoinwallet.presentation.BitcoinWalletApplication.Companion.applicationComponent
import com.beetrack.bitcoinwallet.presentation.di.component.ApplicationComponent
import com.beetrack.bitcoinwallet.presentation.di.component.DaggerApplicationComponent

class BitcoinWalletApplication : Application() {

    companion object {
        lateinit var applicationComponent: ApplicationComponent
    }

    override fun onCreate() {
        super.onCreate()
        applicationComponent =
            DaggerApplicationComponent
                .builder()
                .context(this)
                .build()
        applicationComponent.inject(this)
    }
}

fun appComponent() = applicationComponent