package cl.iroman.bitcoinwallet.data.mapper

import cl.iroman.bitcoinwallet.data.remote.data.AddressFullTransactionData
import cl.iroman.bitcoinwallet.domain.model.AddressTransactionModel

fun AddressFullTransactionData.toModel(): AddressTransactionModel =
    AddressTransactionModel(this.txs?.map {
        AddressTransactionModel.TransactionItem(it?.outputs?.firstOrNull { outputsItem ->
            outputsItem?.scriptType == "pay-to-pubkey-hash"
        }?.value,
            it?.received)
    })