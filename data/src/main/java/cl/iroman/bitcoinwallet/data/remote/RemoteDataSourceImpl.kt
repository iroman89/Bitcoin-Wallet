package cl.iroman.bitcoinwallet.data.remote

import cl.iroman.bitcoinwallet.data.remote.api.BlockCypherAPI
import cl.iroman.bitcoinwallet.data.remote.data.AddressBalanceData
import cl.iroman.bitcoinwallet.data.remote.data.AddressFullTransactionData
import cl.iroman.bitcoinwallet.data.remote.data.AddressKeychainData
import cl.iroman.bitcoinwallet.data.repository.source.RemoteDataSource
import retrofit2.await
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(private val blockCypherAPI: BlockCypherAPI) :
    RemoteDataSource {

    override suspend fun generateAddress(): AddressKeychainData =
        blockCypherAPI.generateAddress().await()

    override suspend fun getAddressBalance(address: String): AddressBalanceData =
        blockCypherAPI.getAddressBalance(address).await()

    override suspend fun getAddressFullTransaction(address: String): AddressFullTransactionData =
        blockCypherAPI.getAddressFullTransactions(address).await()
}