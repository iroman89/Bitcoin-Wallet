package com.beetrack.bitcoinwallet.presentation.ui.address.generator.viewModel

import com.beetrack.bitcoinwallet.domain.util.Failure

sealed class SaveAddressState<T>(
    val data: T? = null,
    val failure: Failure? = null,
) {
    class Loading<T> : SaveAddressState<T>()
    class Saved<T> : SaveAddressState<T>()
    class Error<T>(failure: Failure) : SaveAddressState<T>(failure = failure)
}