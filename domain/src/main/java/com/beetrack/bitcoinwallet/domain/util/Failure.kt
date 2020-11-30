package com.beetrack.bitcoinwallet.domain.util


sealed class Failure {
    data class Error(val throwable: Throwable) : Failure()
    object Empty : Failure()
    object NoDataToSave : Failure()
    object NetworkConnection : Failure()
    object ErrorSaveData : Failure()
}

fun Throwable.toFailure() =
    Failure.Error(this)
