package com.beetrack.bitcoinwallet.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.beetrack.bitcoinwallet.data.local.entity.AddressBalanceEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AddressBalanceDao {

    @Query("SELECT * FROM AddressBalance")
    fun get(): Flow<List<AddressBalanceEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(address: AddressBalanceEntity)

    @Query("DELETE FROM AddressBalance")
    fun deleteAll()
}