package com.beetrack.bitcoinwallet.domain.useCase

import com.beetrack.bitcoinwallet.domain.model.address.AddressKeychainModel
import com.beetrack.bitcoinwallet.domain.repository.BlockCypherRepository
import com.beetrack.bitcoinwallet.domain.util.BaseUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

open class GetAddressUseCase @Inject constructor(private val repository: BlockCypherRepository) :
    BaseUseCase<Flow<AddressKeychainModel>, Boolean>() {

    override suspend fun invoke(params: Boolean): Flow<AddressKeychainModel> =
        repository.getAddress(params)
}