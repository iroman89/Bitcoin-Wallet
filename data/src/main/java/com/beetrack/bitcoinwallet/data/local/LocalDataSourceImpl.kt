package com.beetrack.bitcoinwallet.data.local

import com.beetrack.bitcoinwallet.data.local.entity.AddressKeychainEntity
import com.beetrack.bitcoinwallet.data.repository.source.LocalDataSource
import javax.inject.Inject

class LocalDataSourceImpl @Inject constructor() : LocalDataSource {

    override fun getAddress(): AddressKeychainEntity {
        TODO("Not yet implemented")
    }
}