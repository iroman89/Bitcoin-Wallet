package cl.iroman.bitcoinwallet.domain.useCase

import cl.iroman.bitcoinwallet.domain.model.AddressTransactionModel
import cl.iroman.bitcoinwallet.domain.repository.BlockCypherRepository
import cl.iroman.bitcoinwallet.domain.util.BaseUseCase
import javax.inject.Inject

open class GetTransactionsUseCase @Inject constructor(private val repository: BlockCypherRepository) :
    BaseGetTransactionsUseCase() {

    override suspend fun invoke(params: Nothing?): AddressTransactionModel =
        repository.getAddressTransaction()
}

typealias BaseGetTransactionsUseCase = BaseUseCase<AddressTransactionModel, Nothing?>