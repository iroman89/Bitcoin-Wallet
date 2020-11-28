package com.beetrack.bitcoinwallet.data.repository

import com.beetrack.bitcoinwallet.data.mapper.toModel
import com.beetrack.bitcoinwallet.data.repository.source.LocalDataSource
import com.beetrack.bitcoinwallet.data.repository.source.RemoteDataSource
import com.beetrack.bitcoinwallet.domain.model.address.AddressKeychainModel
import com.beetrack.bitcoinwallet.domain.repository.BlockCypherRepository
import javax.inject.Inject

class BlockCypherRepositoryImpl @Inject constructor(
    private val remote: RemoteDataSource,
    private val local: LocalDataSource
) : BlockCypherRepository {

    @Throws(Exception::class)
    override suspend fun generateAddress(): AddressKeychainModel =
        remote.generateAddress().toModel()
}