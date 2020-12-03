package com.beetrack.bitcoinwallet.data.repository.source

import com.beetrack.bitcoinwallet.data.remote.data.AddressBalanceData
import com.beetrack.bitcoinwallet.data.remote.data.AddressFullTransactionData
import com.beetrack.bitcoinwallet.data.remote.data.AddressKeychainData

interface RemoteDataSource {

    suspend fun generateAddress(): AddressKeychainData

    suspend fun getAddressBalance(address: String): AddressBalanceData

    suspend fun getAddressFullTransaction(address: String): AddressFullTransactionData
}