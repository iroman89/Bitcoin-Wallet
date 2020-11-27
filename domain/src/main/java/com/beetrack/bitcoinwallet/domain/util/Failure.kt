package com.beetrack.bitcoinwallet.domain.util


sealed class Failure {
    data class Error(val throwable: Throwable) : Failure()
    object NetworkConnection : Failure()

    abstract class FeatureFailure : Failure()
}

fun Throwable.toFailure() =
    Failure.Error(this)
