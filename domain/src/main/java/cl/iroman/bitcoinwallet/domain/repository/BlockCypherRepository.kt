package cl.iroman.bitcoinwallet.domain.repository

import cl.iroman.bitcoinwallet.domain.model.AddressBalanceModel
import cl.iroman.bitcoinwallet.domain.model.AddressKeychainModel
import cl.iroman.bitcoinwallet.domain.model.AddressTransactionModel
import kotlinx.coroutines.flow.Flow

interface BlockCypherRepository {

    @Throws(Exception::class)
    suspend fun getAddress(): Flow<AddressKeychainModel>

    @Throws(Exception::class)
    suspend fun generateAddress(): AddressKeychainModel

    @Throws(Exception::class)
    suspend fun saveAddress(address: AddressKeychainModel)

    @Throws(Exception::class)
    suspend fun getAddressBalance(): AddressBalanceModel

    @Throws(Exception::class)
    suspend fun getAddressTransaction(): AddressTransactionModel
}