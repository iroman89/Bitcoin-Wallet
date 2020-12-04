package cl.iroman.bitcoinwallet.domain.util


sealed class Failure {
    data class Error(val throwable: Throwable) : Failure()
    object Empty : Failure()
    object NoDataToSave : Failure()
    object NoTransaction : Failure()
    object NetworkConnection : Failure()
}

fun Throwable.toFailure() =
    Failure.Error(this)
