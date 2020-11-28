package com.beetrack.bitcoinwallet.data.remote

import com.beetrack.bitcoinwallet.data.remote.data.AddressKeychainData
import retrofit2.Call
import retrofit2.http.POST

interface BlockCypherAPI {

    @POST("addrs")
    fun generateAddress(): Call<AddressKeychainData>
}