@file:Suppress("UNCHECKED_CAST")

package com.beetrack.bitcoinwallet.presentation.util

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.beetrack.bitcoinwallet.domain.util.Failure

abstract class BaseViewModel : ViewModel() {

    protected fun <T : Resource<R>, R> MutableLiveData<T>.postLoading() {
        this.postValue(Resource.Loading<T>() as T)
    }

    protected fun <T : Resource<R>, R> MutableLiveData<T>.postFailure(failure: Failure) {
        this.postValue(Resource.Error<T>(failure) as T)
    }

    protected fun <T : Resource<R>, R> MutableLiveData<T>.postSuccess(data: R) {
        this.postValue(Resource.Success(data) as T)
    }
}