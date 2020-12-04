package cl.iroman.bitcoinwallet.data.remote.data

import com.google.gson.annotations.SerializedName

data class AddressBalanceData(

	@field:SerializedName("final_balance")
	val finalBalance: Long? = null,

	@field:SerializedName("unconfirmed_n_tx")
	val unconfirmedNTx: Long? = null,

	@field:SerializedName("address")
	val address: String,

	@field:SerializedName("balance")
	val balance: Long? = null,

	@field:SerializedName("final_n_tx")
	val finalNTx: Long? = null,

	@field:SerializedName("n_tx")
	val nTx: Long? = null,

	@field:SerializedName("total_sent")
	val totalSent: Long? = null,

	@field:SerializedName("unconfirmed_balance")
	val unconfirmedBalance: Long? = null,

	@field:SerializedName("total_received")
	val totalReceived: Long? = null,
)
