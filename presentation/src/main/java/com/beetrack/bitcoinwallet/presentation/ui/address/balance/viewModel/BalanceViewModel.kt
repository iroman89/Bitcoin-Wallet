package com.beetrack.bitcoinwallet.presentation.ui.address.balance.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.beetrack.bitcoinwallet.domain.model.AddressBalanceModel
import com.beetrack.bitcoinwallet.domain.useCase.GetAddressBalanceUseCase
import com.beetrack.bitcoinwallet.domain.util.Failure
import com.beetrack.bitcoinwallet.domain.util.toFailure
import com.beetrack.bitcoinwallet.presentation.util.BaseViewModel
import com.beetrack.bitcoinwallet.presentation.util.ResourceState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

class BalanceViewModel @Inject constructor(private val getAddressBalanceUseCase: GetAddressBalanceUseCase) :
    BaseViewModel() {

    private val _addressBalanceLiveData: MutableLiveData<ResourceState<AddressBalanceModel>> =
        MutableLiveData()
    val addressBalanceLiveData: LiveData<ResourceState<AddressBalanceModel>> =
        _addressBalanceLiveData

    fun getAddressBalance() {
        viewModelScope.launch {
            _addressBalanceLiveData.postLoading()

            runCatching {
                getAddressBalanceUseCase.invoke(null)
            }.onSuccess {
                _addressBalanceLiveData.postSuccess(it)
            }.onFailure {
                when (it) {
                    is IllegalArgumentException -> {
                        delay(600)
                        _addressBalanceLiveData.postFailure(Failure.Empty)
                    }
                    else -> _addressBalanceLiveData.postFailure(it.toFailure())
                }
            }
        }
    }
}