package com.beetrack.bitcoinwallet.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.beetrack.bitcoinwallet.data.local.DatabaseConstants.DATABASE_VERSION
import com.beetrack.bitcoinwallet.data.local.dao.AddressDao
import com.beetrack.bitcoinwallet.data.local.entity.AddressKeychainEntity

@Database(
    entities = [AddressKeychainEntity::class],
    version = DATABASE_VERSION
)
abstract class BitcoinWalletDatabase : RoomDatabase() {
    abstract val addressDao: AddressDao
}