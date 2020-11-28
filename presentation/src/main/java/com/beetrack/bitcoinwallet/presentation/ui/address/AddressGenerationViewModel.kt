package com.beetrack.bitcoinwallet.presentation.ui.address

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.beetrack.bitcoinwallet.domain.model.address.AddressKeychainModel
import com.beetrack.bitcoinwallet.domain.useCase.GetAddressUseCase
import com.beetrack.bitcoinwallet.domain.util.toFailure
import com.beetrack.bitcoinwallet.presentation.util.BaseViewModel
import com.beetrack.bitcoinwallet.presentation.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class AddressGenerationViewModel @Inject constructor(
    private val getAddressUseCase: GetAddressUseCase
) : BaseViewModel() {

    private val _generateAddressLiveData: MutableLiveData<Resource<AddressKeychainModel>> =
        MutableLiveData()
    val generateAddressLiveData: LiveData<Resource<AddressKeychainModel>> =
        _generateAddressLiveData

    fun fetchAddress(generateNew: Boolean = false) = viewModelScope.launch(Dispatchers.IO) {
        _generateAddressLiveData.postLoading()

        runCatching {
            getAddressUseCase.invoke(generateNew)
        }.onSuccess {
            it.collect { address ->
                _generateAddressLiveData.postSuccess(address)
            }
        }.onFailure {
            _generateAddressLiveData.postFailure(it.toFailure())
        }
    }
}


