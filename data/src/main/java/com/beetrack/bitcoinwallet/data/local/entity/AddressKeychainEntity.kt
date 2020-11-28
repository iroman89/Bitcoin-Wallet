package com.beetrack.bitcoinwallet.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.beetrack.bitcoinwallet.data.local.DatabaseConstants

@Entity(tableName = DatabaseConstants.ADDRESS_TABLE)
data class AddressKeychainEntity(

	@PrimaryKey
	@ColumnInfo(name = "address")
	val address: String? = null,

	@ColumnInfo(name = "jsonMemberPrivate")
	val jsonMemberPrivate: String? = null,

	@ColumnInfo(name = "jsonMemberPublic")
	val jsonMemberPublic: String? = null,

	@ColumnInfo(name = "wif")
	val wif: String? = null
)
