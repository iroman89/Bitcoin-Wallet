package com.beetrack.bitcoinwallet.data.network

import com.beetrack.bitcoinwallet.domain.model.address.AddressKeychainResponse
import retrofit2.http.POST

interface BlockCypherAPI {

    @POST("addrs")
    fun generateAddress(): AddressKeychainResponse
}