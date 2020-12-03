package com.beetrack.bitcoinwallet.presentation.util.extension

import android.app.Activity
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.DialogInterface
import android.net.ConnectivityManager
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

fun Context.showMessageOKCancel(
    title: String?,
    message: String?,
    okText: String,
    okListener: DialogInterface.OnClickListener?,
    cancelText: String?,
    cancelListener: DialogInterface.OnClickListener?,
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

@Suppress("DEPRECATION")
fun Context.hasNetworkAvailable(): Boolean {
    val service = Context.CONNECTIVITY_SERVICE
    val manager = this.getSystemService(service) as ConnectivityManager?
    return manager?.activeNetworkInfo?.isConnected ?: false
}

fun Context.toast(message: String, duration: Int = Toast.LENGTH_LONG) {
    Toast.makeText(this, message, duration).show()
}

fun Activity.setToolbarTitle(title: String) {
    (this as? AppCompatActivity)?.supportActionBar?.title = title
}

fun Context.copyInClipboard(label: String, text: String) {
    (getSystemService(Context.CLIPBOARD_SERVICE) as? ClipboardManager?)?.apply {
        this.setPrimaryClip(ClipData.newPlainText(label, text))
    }
    toast("$label copied!")
}