package com.beetrack.bitcoinwallet.data.mapper

import com.beetrack.bitcoinwallet.data.remote.data.AddressBalanceData
import com.beetrack.bitcoinwallet.domain.model.AddressBalanceModel

fun AddressBalanceData.toModel(): AddressBalanceModel =
    AddressBalanceModel(this.address,
        this.finalBalance,
        this.balance,
        this.unconfirmedBalance)
