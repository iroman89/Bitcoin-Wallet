package com.beetrack.bitcoinwallet.domain.useCase

import com.beetrack.bitcoinwallet.domain.model.address.AddressKeychainModel
import com.beetrack.bitcoinwallet.domain.repository.BlockCypherRepository
import com.beetrack.bitcoinwallet.domain.util.BaseUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

open class GetAddressUseCase @Inject constructor(private val repository: BlockCypherRepository) :
    BaseGetAddressUseCase() {

    override suspend fun invoke(params: Nothing?): Flow<AddressKeychainModel> =
        repository.getAddress()
}

typealias BaseGetAddressUseCase = BaseUseCase<Flow<AddressKeychainModel>, Nothing?>