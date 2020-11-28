package com.beetrack.bitcoinwallet.data.repository.source

import com.beetrack.bitcoinwallet.data.local.entity.AddressKeychainEntity

interface LocalDataSource {

    fun getAddress(): AddressKeychainEntity
}