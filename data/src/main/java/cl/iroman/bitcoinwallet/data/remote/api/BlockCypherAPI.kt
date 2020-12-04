package cl.iroman.bitcoinwallet.data.remote.api

import cl.iroman.bitcoinwallet.data.remote.data.AddressBalanceData
import cl.iroman.bitcoinwallet.data.remote.data.AddressFullTransactionData
import cl.iroman.bitcoinwallet.data.remote.data.AddressKeychainData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface BlockCypherAPI {

    @POST("addrs")
    fun generateAddress(): Call<AddressKeychainData>

    @GET("addrs/{address}/balance")
    fun getAddressBalance(@Path("address") address: String): Call<AddressBalanceData>

    @GET("addrs/{address}/full")
    fun getAddressFullTransactions(@Path("address") address: String): Call<AddressFullTransactionData>
}