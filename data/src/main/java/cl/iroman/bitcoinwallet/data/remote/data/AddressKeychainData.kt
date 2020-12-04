package cl.iroman.bitcoinwallet.data.remote.data

import com.google.gson.annotations.SerializedName

data class AddressKeychainData(

	@field:SerializedName("private")
	val jsonMemberPrivate: String? = null,

	@field:SerializedName("address")
	val address: String? = null,

	@field:SerializedName("public")
	val jsonMemberPublic: String? = null,

	@field:SerializedName("wif")
	val wif: String? = null
)
