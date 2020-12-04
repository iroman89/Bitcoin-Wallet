package cl.iroman.bitcoinwallet.presentation.util

import android.annotation.SuppressLint
import java.text.SimpleDateFormat

object DateFormat {

    @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
    @SuppressLint("SimpleDateFormat")
    fun format(
        value: String?,
        patternOut: String,
        patternIn: String = "yyyy-MM-dd'T'HH:mm:ss"
    ): String {
        return value?.let {
            SimpleDateFormat(patternOut).format(SimpleDateFormat(patternIn).parse(value))
        } ?: let {
            ""
        }
    }
}