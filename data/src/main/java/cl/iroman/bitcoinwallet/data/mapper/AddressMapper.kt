package cl.iroman.bitcoinwallet.data.mapper

import cl.iroman.bitcoinwallet.data.local.entity.AddressKeychainEntity
import cl.iroman.bitcoinwallet.data.remote.data.AddressKeychainData
import cl.iroman.bitcoinwallet.domain.model.AddressKeychainModel

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

fun AddressKeychainModel.toEntity(): AddressKeychainEntity =
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