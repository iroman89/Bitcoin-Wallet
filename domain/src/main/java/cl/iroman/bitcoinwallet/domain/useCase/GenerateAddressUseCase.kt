package cl.iroman.bitcoinwallet.domain.useCase

import cl.iroman.bitcoinwallet.domain.model.AddressKeychainModel
import cl.iroman.bitcoinwallet.domain.repository.BlockCypherRepository
import cl.iroman.bitcoinwallet.domain.util.BaseUseCase
import javax.inject.Inject

open class GenerateAddressUseCase @Inject constructor(private val repository: BlockCypherRepository) :
    BaseGenerateAddressUseCase() {

    override suspend fun invoke(params: Nothing?): AddressKeychainModel =
        repository.generateAddress()
}

typealias BaseGenerateAddressUseCase = BaseUseCase<AddressKeychainModel, Nothing?>