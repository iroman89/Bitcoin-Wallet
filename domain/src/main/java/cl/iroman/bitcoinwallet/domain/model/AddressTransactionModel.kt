package cl.iroman.bitcoinwallet.domain.model

data class AddressTransactionModel(

    val transactions: List<TransactionItem>? = null,

    ) {
    data class TransactionItem(

        val total: Long? = null,

        val received: String? = null,
    )
}
