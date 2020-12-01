package com.beetrack.bitcoinwallet.presentation.di.module

import android.content.Context
import androidx.room.Room
import com.beetrack.bitcoinwallet.data.local.DatabaseConstants
import com.beetrack.bitcoinwallet.data.local.dao.AddressBalanceDao
import com.beetrack.bitcoinwallet.data.local.dao.AddressDao
import com.beetrack.bitcoinwallet.data.local.database.BitcoinWalletDatabase
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

    @Provides
    @Singleton
    fun provideAddressBalanceDao(database: BitcoinWalletDatabase): AddressBalanceDao =
        database.addressBalanceDao
}