package cl.iroman.bitcoinwallet.data.mapper

import cl.iroman.bitcoinwallet.data.remote.data.AddressBalanceData
import cl.iroman.bitcoinwallet.domain.model.AddressBalanceModel

fun AddressBalanceData.toModel(): AddressBalanceModel =
    AddressBalanceModel(this.address,
        this.finalBalance,
        this.balance,
        this.unconfirmedBalance)
