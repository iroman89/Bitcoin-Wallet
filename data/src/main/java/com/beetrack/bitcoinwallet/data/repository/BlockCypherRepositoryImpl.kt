package com.beetrack.bitcoinwallet.data.repository

import com.beetrack.bitcoinwallet.data.remote.BlockCypherAPI
import com.beetrack.bitcoinwallet.domain.model.address.AddressKeychainItem
import com.beetrack.bitcoinwallet.domain.repository.BlockCypherRepository
import retrofit2.await
import javax.inject.Inject

class BlockCypherRepositoryImpl @Inject constructor(private val api: BlockCypherAPI) :
    BlockCypherRepository {

    @Throws(Exception::class)
    override suspend fun generateAddress(): AddressKeychainItem =
        api.generateAddress().await()
}