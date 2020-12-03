@file:Suppress("EXPERIMENTAL_API_USAGE")

package com.beetrack.bitcoinwallet.data.local

import com.beetrack.bitcoinwallet.data.local.dao.AddressDao
import com.beetrack.bitcoinwallet.data.local.entity.AddressKeychainEntity
import com.beetrack.bitcoinwallet.data.repository.source.LocalDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataSourceImpl @Inject constructor(
    private val addressDao: AddressDao,
) : LocalDataSource {

    @Throws(Exception::class)
    override fun getAddress(): Flow<List<AddressKeychainEntity>> =
        addressDao.get()

    @Throws(Exception::class)
    override fun insertAddress(address: AddressKeychainEntity) {
        addressDao.deleteAll()
        addressDao.insert(address)
    }
}