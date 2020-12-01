package com.beetrack.bitcoinwallet.data.mapper

import com.beetrack.bitcoinwallet.data.local.entity.AddressBalanceEntity
import com.beetrack.bitcoinwallet.data.remote.data.AddressBalanceData
import com.beetrack.bitcoinwallet.domain.model.addressBalance.AddressBalanceModel

fun AddressBalanceData.toEntity(): AddressBalanceEntity =
    AddressBalanceEntity(this.address,
        this.finalBalance,
        this.unconfirmedNTx,
        this.balance,
        this.finalNTx,
        this.nTx,
        this.totalSent,
        this.unconfirmedBalance,
        this.totalReceived
    )

fun AddressBalanceEntity.toModel(): AddressBalanceModel =
    AddressBalanceModel(this.address,
        this.finalBalance,
        this.balance,
        this.unconfirmedBalance)
