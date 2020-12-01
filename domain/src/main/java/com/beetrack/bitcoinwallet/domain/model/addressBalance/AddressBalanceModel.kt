package com.beetrack.bitcoinwallet.domain.model.addressBalance

data class AddressBalanceModel(

    val address: String,

    val finalBalance: Int? = null,

    val balance: Int? = null,

    val unconfirmedBalance: Int? = null,
)
