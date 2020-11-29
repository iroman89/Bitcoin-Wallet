package com.beetrack.bitcoinwallet.domain.useCase

import com.beetrack.bitcoinwallet.domain.model.address.AddressKeychainModel
import com.beetrack.bitcoinwallet.domain.repository.BlockCypherRepository
import com.beetrack.bitcoinwallet.domain.util.BaseUseCase
import javax.inject.Inject

open class SaveAddressUseCase @Inject constructor(private val repository: BlockCypherRepository) :
    BaseSaveAddressUseCase() {

    override suspend fun invoke(params: AddressKeychainModel) {
        repository.saveAddress(params)
    }
}

typealias BaseSaveAddressUseCase = BaseUseCase<Unit, AddressKeychainModel>