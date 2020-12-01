package com.beetrack.bitcoinwallet.presentation.ui.address.generator.viewModel

import com.beetrack.bitcoinwallet.domain.util.Failure

sealed class AddressState<T>(
    val data: T? = null,
    val failure: Failure? = null,
) {
    class Loading<T> : AddressState<T>()
    class Got<T>(data: T) : AddressState<T>(data)
    class Generated<T>(data: T) : AddressState<T>(data)
    class Saved<T> : AddressState<T>()
    class Error<T>(failure: Failure) : AddressState<T>(failure = failure)
}