package cl.iroman.bitcoinwallet.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import cl.iroman.bitcoinwallet.data.local.entity.AddressKeychainEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AddressDao {

    @Query("SELECT * FROM Address")
    fun get(): Flow<List<AddressKeychainEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(address: AddressKeychainEntity)

    @Query("DELETE FROM Address")
    fun deleteAll()
}