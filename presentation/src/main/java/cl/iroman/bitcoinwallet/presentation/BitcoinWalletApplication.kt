package cl.iroman.bitcoinwallet.presentation

import android.app.Application
import cl.iroman.bitcoinwallet.presentation.BitcoinWalletApplication.Companion.applicationComponent
import cl.iroman.bitcoinwallet.presentation.di.component.ApplicationComponent
import cl.iroman.bitcoinwallet.presentation.di.component.DaggerApplicationComponent

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