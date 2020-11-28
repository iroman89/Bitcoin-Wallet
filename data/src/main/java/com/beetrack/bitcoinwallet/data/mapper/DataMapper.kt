package com.beetrack.bitcoinwallet.data.mapper

import com.beetrack.bitcoinwallet.data.local.entity.AddressKeychainEntity
import com.beetrack.bitcoinwallet.data.remote.data.AddressKeychainData
import com.beetrack.bitcoinwallet.domain.model.address.AddressKeychainModel

fun AddressKeychainData.toModel(): AddressKeychainModel =
    AddressKeychainModel(
        this.jsonMemberPrivate,
        this.address,
        this.jsonMemberPublic,
        this.wif
    )

fun AddressKeychainData.toEntity(): AddressKeychainEntity =
    AddressKeychainEntity(
        this.address ?: "",
        this.jsonMemberPrivate ?: "",
        this.jsonMemberPublic ?: "",
        this.wif ?: ""
    )

fun AddressKeychainEntity.toModel(): AddressKeychainModel =
    AddressKeychainModel(
        this.jsonMemberPrivate,
        this.address,
        this.jsonMemberPublic,
        this.wif
    )