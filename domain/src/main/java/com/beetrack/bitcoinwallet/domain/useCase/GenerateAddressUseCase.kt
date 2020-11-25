package com.beetrack.bitcoinwallet.domain.useCase

import com.beetrack.bitcoinwallet.domain.model.address.AddressKeychainResponse
import com.beetrack.bitcoinwallet.domain.repository.BlockCypherRepository
import com.beetrack.bitcoinwallet.domain.util.BaseUseCase
import javax.inject.Inject

open class GenerateAddressUseCase @Inject constructor(private val repository: BlockCypherRepository) :
    BaseUseCase<AddressKeychainResponse, Nothing>() {

    override suspend fun invoke(request: Nothing): AddressKeychainResponse =
        repository.generateAddress()
}