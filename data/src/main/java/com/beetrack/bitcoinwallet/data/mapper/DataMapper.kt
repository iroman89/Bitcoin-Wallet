package com.beetrack.bitcoinwallet.data.mapper

import com.beetrack.bitcoinwallet.data.remote.data.AddressKeychainData
import com.beetrack.bitcoinwallet.domain.model.address.AddressKeychainModel

fun AddressKeychainData.toModel(): AddressKeychainModel =
    AddressKeychainModel(
        this.jsonMemberPrivate,
        this.address,
        this.jsonMemberPublic,
        this.wif
    )