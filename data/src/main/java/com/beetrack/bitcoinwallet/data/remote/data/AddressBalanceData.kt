package com.beetrack.bitcoinwallet.data.remote.data

import com.google.gson.annotations.SerializedName

data class AddressBalanceData(

	@field:SerializedName("final_balance")
	val finalBalance: Int? = null,

	@field:SerializedName("unconfirmed_n_tx")
	val unconfirmedNTx: Int? = null,

	@field:SerializedName("address")
	val address: String,

	@field:SerializedName("balance")
	val balance: Int? = null,

	@field:SerializedName("final_n_tx")
	val finalNTx: Int? = null,

	@field:SerializedName("n_tx")
	val nTx: Int? = null,

	@field:SerializedName("total_sent")
	val totalSent: Int? = null,

	@field:SerializedName("unconfirmed_balance")
	val unconfirmedBalance: Int? = null,

	@field:SerializedName("total_received")
	val totalReceived: Int? = null,
)
