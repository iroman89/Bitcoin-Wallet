package com.beetrack.bitcoinwallet.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.beetrack.bitcoinwallet.data.local.entity.AddressBalanceEntity

@Dao
interface AddressBalanceDao {

    @Query("SELECT * FROM AddressBalance")
    fun get(): List<AddressBalanceEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(address: AddressBalanceEntity)
}