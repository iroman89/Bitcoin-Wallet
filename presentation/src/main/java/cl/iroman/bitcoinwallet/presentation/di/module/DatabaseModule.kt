package cl.iroman.bitcoinwallet.presentation.di.module

import android.content.Context
import androidx.room.Room
import cl.iroman.bitcoinwallet.data.local.DatabaseConstants
import cl.iroman.bitcoinwallet.data.local.dao.AddressDao
import cl.iroman.bitcoinwallet.data.local.database.BitcoinWalletDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Provides
    @Singleton
    fun provideRoomDatabase(context: Context): BitcoinWalletDatabase =
        Room.databaseBuilder(
            context,
            BitcoinWalletDatabase::class.java,
            DatabaseConstants.DATABASE_NAME
        ).allowMainThreadQueries().build()

    @Provides
    @Singleton
    fun provideAddressDao(database: BitcoinWalletDatabase): AddressDao =
        database.addressDao
}