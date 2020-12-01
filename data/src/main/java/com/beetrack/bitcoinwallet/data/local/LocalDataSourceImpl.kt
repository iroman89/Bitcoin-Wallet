@file:Suppress("EXPERIMENTAL_API_USAGE")

package com.beetrack.bitcoinwallet.data.local

import com.beetrack.bitcoinwallet.data.local.dao.AddressBalanceDao
import com.beetrack.bitcoinwallet.data.local.dao.AddressDao
import com.beetrack.bitcoinwallet.data.local.entity.AddressBalanceEntity
import com.beetrack.bitcoinwallet.data.local.entity.AddressKeychainEntity
import com.beetrack.bitcoinwallet.data.repository.source.LocalDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataSourceImpl @Inject constructor(
    private val addressDao: AddressDao,
    private val addressBalanceDao: AddressBalanceDao,
) : LocalDataSource {

    @Throws(Exception::class)
    override fun getAddress(): Flow<List<AddressKeychainEntity>> =
        addressDao.get()

    @Throws(Exception::class)
    override fun insertAddress(address: AddressKeychainEntity) {
        addressDao.deleteAll()
        addressDao.insert(address)
    }

    @Throws(Exception::class)
    override fun getAddressBalance(): Flow<List<AddressBalanceEntity>> =
        addressBalanceDao.get()


    override fun insertAddressBalance(addressBalance: AddressBalanceEntity) {
        addressBalanceDao.deleteAll()
        addressBalanceDao.insert(addressBalance)
    }
}