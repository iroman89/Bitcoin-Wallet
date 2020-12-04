package cl.iroman.bitcoinwallet.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import cl.iroman.bitcoinwallet.data.local.DatabaseConstants.DATABASE_VERSION
import cl.iroman.bitcoinwallet.data.local.dao.AddressDao
import cl.iroman.bitcoinwallet.data.local.entity.AddressKeychainEntity

@Database(
    entities = [
        AddressKeychainEntity::class],
    version = DATABASE_VERSION,
    exportSchema = false
)
abstract class BitcoinWalletDatabase : RoomDatabase() {
    abstract val addressDao: AddressDao
}