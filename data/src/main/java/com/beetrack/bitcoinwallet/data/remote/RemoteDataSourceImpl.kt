package com.beetrack.bitcoinwallet.data.remote

import com.beetrack.bitcoinwallet.data.remote.api.BlockCypherAPI
import com.beetrack.bitcoinwallet.data.remote.data.AddressBalanceData
import com.beetrack.bitcoinwallet.data.remote.data.AddressKeychainData
import com.beetrack.bitcoinwallet.data.repository.source.RemoteDataSource
import retrofit2.await
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(private val blockCypherAPI: BlockCypherAPI) :
    RemoteDataSource {

    override suspend fun generateAddress(): AddressKeychainData =
        blockCypherAPI.generateAddress().await()

    override suspend fun getAddressBalance(address: String): AddressBalanceData =
        blockCypherAPI.getAddressBalance(address).await()
}