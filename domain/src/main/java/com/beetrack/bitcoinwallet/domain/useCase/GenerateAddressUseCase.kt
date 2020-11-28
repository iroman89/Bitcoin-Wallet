package com.beetrack.bitcoinwallet.domain.useCase

import com.beetrack.bitcoinwallet.domain.model.address.AddressKeychainItem
import com.beetrack.bitcoinwallet.domain.repository.BlockCypherRepository
import com.beetrack.bitcoinwallet.domain.util.BaseUseCase
import javax.inject.Inject

open class GenerateAddressUseCase @Inject constructor(private val repository: BlockCypherRepository) :
    BaseUseCase<AddressKeychainItem, Nothing?>() {

    override suspend fun invoke(request: Nothing?): AddressKeychainItem =
        repository.generateAddress()
}