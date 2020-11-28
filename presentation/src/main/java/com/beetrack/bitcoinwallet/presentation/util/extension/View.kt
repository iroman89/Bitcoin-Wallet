package com.beetrack.bitcoinwallet.presentation.util.extension

import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding

fun ViewGroup.inflate(layoutRes: Int, attachToRoot: Boolean = false): View =
    LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)

fun View.visible() {
    this.visibility = View.VISIBLE
}

fun View.invisible() {
    this.visibility = View.INVISIBLE
}

fun View.gone() {
    this.visibility = View.GONE
}

fun View.isVisible(): Boolean {
    return this.visibility == View.VISIBLE
}

fun ViewBinding.visible() {
    this.root.visibility = View.VISIBLE
}

fun ViewBinding.invisible() {
    this.root.visibility = View.INVISIBLE
}

fun ViewBinding.gone() {
    this.root.visibility = View.GONE
}

fun ViewBinding.isVisible(): Boolean {
    return this.root.visibility == View.VISIBLE
}

val Int.dp: Int
    get() = (this * Resources.getSystem().displayMetrics.density + 0.5f).toInt()