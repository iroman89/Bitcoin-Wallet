package cl.iroman.bitcoinwallet.domain.useCase

import cl.iroman.bitcoinwallet.domain.model.AddressBalanceModel
import cl.iroman.bitcoinwallet.domain.repository.BlockCypherRepository
import cl.iroman.bitcoinwallet.domain.util.BaseUseCase
import javax.inject.Inject

open class GetAddressBalanceUseCase @Inject constructor(private val repository: BlockCypherRepository) :
    BaseGetAddressBalanceUseCase() {

    override suspend fun invoke(params: Nothing?): AddressBalanceModel =
        repository.getAddressBalance()
}

typealias BaseGetAddressBalanceUseCase = BaseUseCase<AddressBalanceModel, Nothing?>