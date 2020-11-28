package com.beetrack.bitcoinwallet.domain.repository

import com.beetrack.bitcoinwallet.domain.model.address.AddressKeychainModel

interface BlockCypherRepository {

    @Throws(Exception::class)
    suspend fun generateAddress(): AddressKeychainModel
}