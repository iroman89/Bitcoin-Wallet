package com.beetrack.bitcoinwallet.presentation.util

import android.content.Context
import android.net.ConnectivityManager

@Suppress("DEPRECATION")
fun hasNetworkAvailable(context: Context): Boolean {
    val service = Context.CONNECTIVITY_SERVICE
    val manager = context.getSystemService(service) as ConnectivityManager?
    val network = manager?.activeNetworkInfo
    return (network?.isConnected) ?: false
}