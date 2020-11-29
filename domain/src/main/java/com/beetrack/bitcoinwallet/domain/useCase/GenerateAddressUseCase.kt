package com.beetrack.bitcoinwallet.domain.useCase

import com.beetrack.bitcoinwallet.domain.model.address.AddressKeychainModel
import com.beetrack.bitcoinwallet.domain.repository.BlockCypherRepository
import com.beetrack.bitcoinwallet.domain.util.BaseUseCase
import javax.inject.Inject

open class GenerateAddressUseCase @Inject constructor(private val repository: BlockCypherRepository) :
    BaseGenerateAddressUseCase() {

    override suspend fun invoke(params: Nothing?): AddressKeychainModel =
        repository.generateAddress()
}

typealias BaseGenerateAddressUseCase = BaseUseCase<AddressKeychainModel, Nothing?>