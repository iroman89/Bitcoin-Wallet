package cl.iroman.bitcoinwallet.data.remote.data

import com.google.gson.annotations.SerializedName

data class AddressFullTransactionData(

	@field:SerializedName("final_balance")
	val finalBalance: Long? = null,

	@field:SerializedName("unconfirmed_n_tx")
	val unconfirmedNTx: Long? = null,

	@field:SerializedName("address")
	val address: String? = null,

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

	@field:SerializedName("txs")
	val txs: List<TxsItem?>? = null,
)

data class OutputsItem(

	@field:SerializedName("addresses")
	val addresses: List<String?>? = null,

	@field:SerializedName("spent_by")
	val spentBy: String? = null,

	@field:SerializedName("script_type")
	val scriptType: String? = null,

	@field:SerializedName("value")
	val value: Long? = null,

	@field:SerializedName("script")
	val script: String? = null,
)

data class TxsItem(

	@field:SerializedName("outputs")
	val outputs: List<OutputsItem?>? = null,

	@field:SerializedName("addresses")
	val addresses: List<String?>? = null,

	@field:SerializedName("fees")
	val fees: Long? = null,

	@field:SerializedName("ver")
	val ver: Long? = null,

	@field:SerializedName("inputs")
	val inputs: List<InputsItem?>? = null,

	@field:SerializedName("preference")
	val preference: String? = null,

	@field:SerializedName("confidence")
	val confidence: Long? = null,

	@field:SerializedName("block_hash")
	val blockHash: String? = null,

	@field:SerializedName("received")
	val received: String? = null,

	@field:SerializedName("block_height")
	val blockHeight: Long? = null,

	@field:SerializedName("confirmations")
	val confirmations: Long? = null,

	@field:SerializedName("confirmed")
	val confirmed: String? = null,

	@field:SerializedName("relayed_by")
	val relayedBy: String? = null,

	@field:SerializedName("lock_time")
	val lockTime: Long? = null,

	@field:SerializedName("total")
	val total: Long? = null,

	@field:SerializedName("size")
	val size: Long? = null,

	@field:SerializedName("double_spend")
	val doubleSpend: Boolean? = null,

	@field:SerializedName("vin_sz")
	val vinSz: Long? = null,

	@field:SerializedName("hash")
	val hash: String? = null,

	@field:SerializedName("vout_sz")
	val voutSz: Long? = null,
)

data class InputsItem(

	@field:SerializedName("sequence")
	val sequence: Long? = null,

	@field:SerializedName("addresses")
	val addresses: List<String?>? = null,

	@field:SerializedName("prev_hash")
	val prevHash: String? = null,

	@field:SerializedName("output_value")
	val outputValue: Long? = null,

	@field:SerializedName("script_type")
	val scriptType: String? = null,

	@field:SerializedName("output_index")
	val outputIndex: Long? = null,

	@field:SerializedName("script")
	val script: String? = null,
)
