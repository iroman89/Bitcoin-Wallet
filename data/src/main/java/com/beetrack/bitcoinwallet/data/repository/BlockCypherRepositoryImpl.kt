package com.beetrack.bitcoinwallet.data.repository

import com.beetrack.bitcoinwallet.data.network.BlockCypherAPI
import com.beetrack.bitcoinwallet.domain.model.address.AddressKeychainResponse
import com.beetrack.bitcoinwallet.domain.repository.BlockCypherRepository
import javax.inject.Inject

class BlockCypherRepositoryImpl @Inject constructor(private val api: BlockCypherAPI) :
    BlockCypherRepository {

    @Throws(Exception::class)
    override suspend fun generateAddress(): AddressKeychainResponse =
        api.generateAddress()
}