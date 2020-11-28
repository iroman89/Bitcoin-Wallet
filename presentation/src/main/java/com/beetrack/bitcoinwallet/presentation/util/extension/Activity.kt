package com.beetrack.bitcoinwallet.presentation.util.extension

import android.content.Context
import android.content.DialogInterface
import androidx.appcompat.app.AlertDialog

fun Context.showMessageOKCancel(
    title: String?,
    message: String?,
    okText: String,
    okListener: DialogInterface.OnClickListener?,
    cancelText: String?,
    cancelListener: DialogInterface.OnClickListener?
) {
    AlertDialog.Builder(this).apply {
        setTitle(title)
        setCancelable(false)
        setMessage(message)
        setPositiveButton(okText, okListener)
        cancelText?.let {
            setNegativeButton(it, cancelListener)
        }
        create()
    }.show()
}