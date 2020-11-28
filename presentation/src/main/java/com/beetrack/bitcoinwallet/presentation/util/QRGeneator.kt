package com.beetrack.bitcoinwallet.presentation.util

import android.graphics.Bitmap
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.journeyapps.barcodescanner.BarcodeEncoder

fun String.toBitmapQR(width: Int = 5_000, height: Int = 5_000): Bitmap? {
    return try {
        val qrResult =
            MultiFormatWriter().encode(this, BarcodeFormat.QR_CODE, width, height)
        BarcodeEncoder().createBitmap(qrResult)
    } catch (e: Throwable) {
        null
    }
}