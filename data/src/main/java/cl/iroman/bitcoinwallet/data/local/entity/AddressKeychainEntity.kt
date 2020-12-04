package cl.iroman.bitcoinwallet.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import cl.iroman.bitcoinwallet.data.local.DatabaseConstants

@Entity(tableName = DatabaseConstants.ADDRESS_TABLE)
data class AddressKeychainEntity(

	@PrimaryKey
	@ColumnInfo(name = "address")
	val address: String,

	@ColumnInfo(name = "jsonMemberPrivate")
	val jsonMemberPrivate: String,

	@ColumnInfo(name = "jsonMemberPublic")
	val jsonMemberPublic: String,

	@ColumnInfo(name = "wif")
	val wif: String,
)
