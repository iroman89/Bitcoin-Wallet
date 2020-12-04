package cl.iroman.bitcoinwallet.domain.useCase

import cl.iroman.bitcoinwallet.domain.model.AddressKeychainModel
import cl.iroman.bitcoinwallet.domain.repository.BlockCypherRepository
import cl.iroman.bitcoinwallet.domain.util.BaseUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

open class GetAddressUseCase @Inject constructor(private val repository: BlockCypherRepository) :
    BaseGetAddressUseCase() {

    override suspend fun invoke(params: Nothing?): Flow<AddressKeychainModel> =
        repository.getAddress()
}

typealias BaseGetAddressUseCase = BaseUseCase<Flow<AddressKeychainModel>, Nothing?>