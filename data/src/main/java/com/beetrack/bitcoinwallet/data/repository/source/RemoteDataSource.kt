package com.beetrack.bitcoinwallet.data.repository.source

import com.beetrack.bitcoinwallet.data.remote.data.AddressKeychainData

interface RemoteDataSource {

    suspend fun generateAddress(): AddressKeychainData
}