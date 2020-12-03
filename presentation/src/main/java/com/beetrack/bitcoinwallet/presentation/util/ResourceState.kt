package com.beetrack.bitcoinwallet.presentation.util

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.beetrack.bitcoinwallet.domain.util.Failure

sealed class ResourceState<T>(
    val data: T? = null,
    val failure: Failure? = null,
) {
    class Success<T>(data: T) : ResourceState<T>(data)
    class Loading<T> : ResourceState<T>()
    class Error<T>(failure: Failure) : ResourceState<T>(failure = failure)
}

@Suppress("UNCHECKED_CAST")
inline fun <T, R> LiveData<T>.subscribe(
    owner: LifecycleOwner,
    crossinline loading: () -> Unit,
    crossinline success: (R) -> Unit,
    crossinline error: (Failure) -> Unit,
) {
    this.observe(owner, Observer {
        when (it) {
            is ResourceState.Loading<*> -> loading()
            is ResourceState.Success<*> -> success(it.data as R)
            is ResourceState.Error<*> -> error(it.failure!!)
        }
    })
}

inline fun <T : ResourceState<R>, R> LiveData<T>.handleValue(
    success: (R) -> Unit,
    error: (Failure) -> Unit,
    getData: () -> Unit,
) {
    this.value?.failure?.also {
        error(it)
    } ?: this.value?.data?.also {
        success(it)
    } ?: getData()
}