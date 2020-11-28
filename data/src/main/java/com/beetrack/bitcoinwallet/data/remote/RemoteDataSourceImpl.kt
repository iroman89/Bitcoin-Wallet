package com.beetrack.bitcoinwallet.data.remote

import com.beetrack.bitcoinwallet.data.remote.data.AddressKeychainData
import com.beetrack.bitcoinwallet.data.repository.source.RemoteDataSource
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor() : RemoteDataSource {

    override suspend fun generateAddress(): AddressKeychainData {
        TODO("Not yet implemented")
    }
}