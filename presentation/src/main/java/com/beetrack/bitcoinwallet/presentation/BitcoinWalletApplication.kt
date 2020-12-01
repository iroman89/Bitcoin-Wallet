package com.beetrack.bitcoinwallet.presentation

import android.app.Application
import androidx.work.Configuration
import androidx.work.WorkManager
import com.beetrack.bitcoinwallet.data.worker.AddressBalanceWorkRequest
import com.beetrack.bitcoinwallet.presentation.BitcoinWalletApplication.Companion.applicationComponent
import com.beetrack.bitcoinwallet.presentation.di.component.ApplicationComponent
import com.beetrack.bitcoinwallet.presentation.di.component.DaggerApplicationComponent
import javax.inject.Inject

class BitcoinWalletApplication : Application() {

    @Inject
    lateinit var workConfiguration: Configuration

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

        WorkManager.initialize(this, workConfiguration)
        WorkManager.getInstance(this).enqueue(AddressBalanceWorkRequest)
    }
}

fun appComponent() = applicationComponent