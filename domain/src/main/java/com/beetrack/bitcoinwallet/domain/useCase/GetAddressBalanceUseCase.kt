package com.beetrack.bitcoinwallet.domain.useCase

import com.beetrack.bitcoinwallet.domain.model.addressBalance.AddressBalanceModel
import com.beetrack.bitcoinwallet.domain.repository.BlockCypherRepository
import com.beetrack.bitcoinwallet.domain.util.BaseUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAddressBalanceUseCase @Inject constructor(private val repository: BlockCypherRepository) :
    BaseGetAddressBalanceUseCase() {

    override suspend fun invoke(params: Nothing?): Flow<AddressBalanceModel> =
        repository.getAddressBalance()
}

typealias BaseGetAddressBalanceUseCase = BaseUseCase<Flow<AddressBalanceModel>, Nothing?>