package com.beetrack.bitcoinwallet.domain.useCase

import com.beetrack.bitcoinwallet.domain.model.AddressTransactionModel
import com.beetrack.bitcoinwallet.domain.repository.BlockCypherRepository
import com.beetrack.bitcoinwallet.domain.util.BaseUseCase
import javax.inject.Inject

class GetTransactionsUseCase @Inject constructor(private val repository: BlockCypherRepository) :
    BaseGetTransactionsUseCase() {

    override suspend fun invoke(params: Nothing?): AddressTransactionModel =
        repository.getAddressTransaction()
}

typealias BaseGetTransactionsUseCase = BaseUseCase<AddressTransactionModel, Nothing?>