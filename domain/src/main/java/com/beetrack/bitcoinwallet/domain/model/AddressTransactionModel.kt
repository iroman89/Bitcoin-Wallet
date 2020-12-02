package com.beetrack.bitcoinwallet.domain.model

data class AddressTransactionModel(

    val transactions: List<TransactionItem>? = null,

    ) {
    data class TransactionItem(

        val total: Int? = null,

        val received: String? = null,
    )
}
