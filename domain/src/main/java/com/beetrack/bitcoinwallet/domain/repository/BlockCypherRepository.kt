package com.beetrack.bitcoinwallet.domain.repository

import com.beetrack.bitcoinwallet.domain.model.AddressBalanceModel
import com.beetrack.bitcoinwallet.domain.model.AddressKeychainModel
import com.beetrack.bitcoinwallet.domain.model.AddressTransactionModel
import kotlinx.coroutines.flow.Flow

interface BlockCypherRepository {

    suspend fun getAddress(): Flow<AddressKeychainModel>

    suspend fun generateAddress(): AddressKeychainModel

    suspend fun saveAddress(address: AddressKeychainModel)

    suspend fun getAddressBalance(): AddressBalanceModel

    suspend fun getAddressTransaction(): AddressTransactionModel
}