package com.beetrack.bitcoinwallet.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.beetrack.bitcoinwallet.data.local.entity.AddressKeychainEntity

@Dao
interface AddressDao {

    @Query("SELECT * FROM address")
    fun getAddress(): AddressKeychainEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(address: AddressKeychainEntity)
}