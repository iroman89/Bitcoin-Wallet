package com.beetrack.bitcoinwallet.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.beetrack.bitcoinwallet.data.local.DatabaseConstants.ADDRESS_BALANCE_TABLE

@Entity(tableName = ADDRESS_BALANCE_TABLE)
data class AddressBalanceEntity(

    @PrimaryKey
    @ColumnInfo(name = "address")
    val address: String,

    @ColumnInfo(name = "final_balance")
    val finalBalance: Int? = null,

    @ColumnInfo(name = "unconfirmed_n_tx")
    val unconfirmedNTx: Int? = null,

    @ColumnInfo(name = "balance")
    val balance: Int? = null,

    @ColumnInfo(name = "final_n_tx")
    val finalNTx: Int? = null,

    @ColumnInfo(name = "n_tx")
    val nTx: Int? = null,

    @ColumnInfo(name = "total_sent")
    val totalSent: Int? = null,

    @ColumnInfo(name = "unconfirmed_balance")
    val unconfirmedBalance: Int? = null,

    @ColumnInfo(name = "total_received")
    val totalReceived: Int? = null,
)
