package com.beetrack.bitcoinwallet.domain.model

data class AddressBalanceModel(

    val address: String,

    val finalBalance: Long? = null,

    val balance: Long? = null,

    val unconfirmedBalance: Long? = null,
)
