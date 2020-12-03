package com.beetrack.bitcoinwallet.data.mapper

import com.beetrack.bitcoinwallet.data.remote.data.AddressFullTransactionData
import com.beetrack.bitcoinwallet.domain.model.AddressTransactionModel

fun AddressFullTransactionData.toModel(): AddressTransactionModel =
    AddressTransactionModel(this.txs?.map {
        AddressTransactionModel.TransactionItem(it?.outputs?.firstOrNull { outputsItem ->
            outputsItem?.scriptType == "pay-to-pubkey-hash"
        }?.value,
            it?.received)
    })