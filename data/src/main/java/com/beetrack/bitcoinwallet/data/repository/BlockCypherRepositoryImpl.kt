package com.beetrack.bitcoinwallet.data.repository

import com.beetrack.bitcoinwallet.data.mapper.toEntity
import com.beetrack.bitcoinwallet.data.mapper.toModel
import com.beetrack.bitcoinwallet.data.repository.source.LocalDataSource
import com.beetrack.bitcoinwallet.data.repository.source.RemoteDataSource
import com.beetrack.bitcoinwallet.domain.model.AddressBalanceModel
import com.beetrack.bitcoinwallet.domain.model.AddressKeychainModel
import com.beetrack.bitcoinwallet.domain.model.AddressTransactionModel
import com.beetrack.bitcoinwallet.domain.repository.BlockCypherRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class BlockCypherRepositoryImpl @Inject constructor(
    private val remote: RemoteDataSource,
    private val local: LocalDataSource,
) : BlockCypherRepository {

    @Throws(Exception::class)
    override suspend fun getAddress(): Flow<AddressKeychainModel> = withContext(Dispatchers.IO) {
        local.getAddress()
            .map {
                it.single().toModel()
            }
    }

    @Throws(Exception::class)
    override suspend fun generateAddress(): AddressKeychainModel = withContext(Dispatchers.IO) {
        remote.generateAddress().toModel()
    }

    @Throws(Exception::class)
    override suspend fun saveAddress(address: AddressKeychainModel) = withContext(Dispatchers.IO) {
        local.insertAddress(address.toEntity())
    }

    @Throws(Exception::class)
    override suspend fun getAddressBalance(): AddressBalanceModel = withContext(Dispatchers.IO) {
        local.getAddress().firstOrNull()?.firstOrNull()?.let {
            remote.getAddressBalance(it.address).toModel()
        } ?: throw IllegalArgumentException("No Address argument")
    }

    @Throws(Exception::class)
    override suspend fun getAddressTransaction(): AddressTransactionModel =
        withContext(Dispatchers.IO) {
            local.getAddress().firstOrNull()?.firstOrNull()?.let {
                remote.getAddressFullTransaction(it.address).toModel()
            } ?: throw IllegalArgumentException("No Address argument")
        }
}