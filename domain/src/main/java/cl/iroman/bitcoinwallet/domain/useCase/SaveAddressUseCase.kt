package cl.iroman.bitcoinwallet.domain.useCase

import cl.iroman.bitcoinwallet.domain.model.AddressKeychainModel
import cl.iroman.bitcoinwallet.domain.repository.BlockCypherRepository
import cl.iroman.bitcoinwallet.domain.util.BaseUseCase
import javax.inject.Inject

open class SaveAddressUseCase @Inject constructor(private val repository: BlockCypherRepository) :
    BaseSaveAddressUseCase() {

    override suspend fun invoke(params: AddressKeychainModel) {
        repository.saveAddress(params)
    }
}

typealias BaseSaveAddressUseCase = BaseUseCase<Unit, AddressKeychainModel>