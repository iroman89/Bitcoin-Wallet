package com.beetrack.bitcoinwallet.presentation.util

import java.io.IOException

class WithoutConnectivityException : IOException() {
    override val message: String?
        get() = "No Internet Connection"
}