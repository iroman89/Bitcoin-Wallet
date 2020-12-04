package cl.iroman.bitcoinwallet.data.repository.source

import cl.iroman.bitcoinwallet.data.local.entity.AddressKeychainEntity
import kotlinx.coroutines.flow.Flow

interface LocalDataSource {

    fun getAddress(): Flow<List<AddressKeychainEntity>>

    fun insertAddress(address: AddressKeychainEntity)
}