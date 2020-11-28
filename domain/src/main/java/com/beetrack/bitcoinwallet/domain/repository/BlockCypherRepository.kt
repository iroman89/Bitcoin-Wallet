package com.beetrack.bitcoinwallet.domain.repository

import com.beetrack.bitcoinwallet.domain.model.address.AddressKeychainModel
import kotlinx.coroutines.flow.Flow

interface BlockCypherRepository {

    @Throws(Exception::class)
    suspend fun getAddress(generateNew: Boolean): Flow<AddressKeychainModel>
}