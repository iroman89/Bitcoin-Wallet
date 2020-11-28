package com.beetrack.bitcoinwallet.data.local

import com.beetrack.bitcoinwallet.data.local.dao.AddressDao
import com.beetrack.bitcoinwallet.data.local.entity.AddressKeychainEntity
import com.beetrack.bitcoinwallet.data.repository.source.LocalDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LocalDataSourceImpl @Inject constructor(private val addressDao: AddressDao) :
    LocalDataSource {

    @Throws(Exception::class)
    override fun getAddress(): Flow<List<AddressKeychainEntity>> = flow {
        emit(addressDao.getAddress())
    }

    @Throws(Exception::class)
    override fun insertAddress(address: AddressKeychainEntity) {
        addressDao.insert(address)
    }

    @Throws(Exception::class)
    override fun deleteAll() {
        addressDao.deleteAll()
    }
}