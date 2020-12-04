package cl.iroman.bitcoinwallet.data.repository.source

import cl.iroman.bitcoinwallet.data.remote.data.AddressBalanceData
import cl.iroman.bitcoinwallet.data.remote.data.AddressFullTransactionData
import cl.iroman.bitcoinwallet.data.remote.data.AddressKeychainData

interface RemoteDataSource {

    suspend fun generateAddress(): AddressKeychainData

    suspend fun getAddressBalance(address: String): AddressBalanceData

    suspend fun getAddressFullTransaction(address: String): AddressFullTransactionData
}