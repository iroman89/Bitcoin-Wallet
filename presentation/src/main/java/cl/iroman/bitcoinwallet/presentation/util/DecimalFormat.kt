package cl.iroman.bitcoinwallet.presentation.util

import java.text.DecimalFormat
import java.util.*


object DecimalFormat {

    fun format(value: Long?): String = DecimalFormat.getInstance(Locale.getDefault()).format(value)
}
