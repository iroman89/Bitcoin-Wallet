package com.beetrack.bitcoinwallet.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.beetrack.bitcoinwallet.data.local.DatabaseConstants.DATABASE_VERSION
import com.beetrack.bitcoinwallet.data.local.dao.AddressDao
import com.beetrack.bitcoinwallet.data.remote.data.AddressKeychainData

@Database(
    entities = [AddressKeychainData::class],
    version = DATABASE_VERSION
)
abstract class BlockCypherDatabase : RoomDatabase() {
    abstract val addressDao: AddressDao
}