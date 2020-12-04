@file:Suppress("UNCHECKED_CAST")

package cl.iroman.bitcoinwallet.presentation.util

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import cl.iroman.bitcoinwallet.domain.util.Failure

abstract class BaseViewModel : ViewModel() {

    protected fun <T : ResourceState<R>, R> MutableLiveData<T>.postLoading() {
        this.postValue(ResourceState.Loading<T>() as T)
    }

    protected fun <T : ResourceState<R>, R> MutableLiveData<T>.postFailure(failure: Failure) {
        this.postValue(ResourceState.Error<T>(failure) as T)
    }

    protected fun <T : ResourceState<R>, R> MutableLiveData<T>.postSuccess(data: R) {
        this.postValue(ResourceState.Success(data) as T)
    }
}