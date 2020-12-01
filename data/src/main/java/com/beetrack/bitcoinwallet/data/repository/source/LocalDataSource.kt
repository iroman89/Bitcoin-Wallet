package com.beetrack.bitcoinwallet.data.repository.source

import com.beetrack.bitcoinwallet.data.local.entity.AddressBalanceEntity
import com.beetrack.bitcoinwallet.data.local.entity.AddressKeychainEntity
import kotlinx.coroutines.flow.Flow

interface LocalDataSource {

    fun getAddress(): Flow<List<AddressKeychainEntity>>

    fun insertAddress(address: AddressKeychainEntity)

    fun deleteAllAddress()

    fun getAddressBalance(): Flow<List<AddressBalanceEntity>>

    fun insertAddressBalance(addressBalance: AddressBalanceEntity)
}