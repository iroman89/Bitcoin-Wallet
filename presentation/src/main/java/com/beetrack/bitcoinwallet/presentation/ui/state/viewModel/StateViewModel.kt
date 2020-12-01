package com.beetrack.bitcoinwallet.presentation.ui.state.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.beetrack.bitcoinwallet.domain.model.addressBalance.AddressBalanceModel
import com.beetrack.bitcoinwallet.domain.useCase.GetAddressBalanceUseCase
import com.beetrack.bitcoinwallet.domain.util.Failure
import com.beetrack.bitcoinwallet.domain.util.toFailure
import com.beetrack.bitcoinwallet.presentation.util.BaseViewModel
import com.beetrack.bitcoinwallet.presentation.util.ResourceState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

class StateViewModel @Inject constructor(private val getAddressBalanceUseCase: GetAddressBalanceUseCase) :
    BaseViewModel() {

    private val _addressBalanceLiveData: MutableLiveData<ResourceState<AddressBalanceModel>> =
        MutableLiveData()
    val addressBalanceLiveData: LiveData<ResourceState<AddressBalanceModel>> =
        _addressBalanceLiveData

    fun getAddressBalance() {
        viewModelScope.launch(Dispatchers.IO) {
            _addressBalanceLiveData.postLoading()

            runCatching {
                getAddressBalanceUseCase.invoke(null)
            }.onSuccess {
                it.catch {
                    _addressBalanceLiveData.postFailure(Failure.Empty)
                }.distinctUntilChanged()
                    .collect { addressBalance ->
                        _addressBalanceLiveData.postSuccess(addressBalance)
                    }
            }.onFailure {
                _addressBalanceLiveData.postFailure(it.toFailure())
            }
        }
    }
}