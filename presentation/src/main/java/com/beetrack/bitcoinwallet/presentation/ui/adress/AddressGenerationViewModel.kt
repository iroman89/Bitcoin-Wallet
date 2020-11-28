package com.beetrack.bitcoinwallet.presentation.ui.adress

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.beetrack.bitcoinwallet.domain.model.address.AddressKeychainItem
import com.beetrack.bitcoinwallet.domain.useCase.GenerateAddressUseCase
import com.beetrack.bitcoinwallet.domain.util.toFailure
import com.beetrack.bitcoinwallet.presentation.util.BaseViewModel
import com.beetrack.bitcoinwallet.presentation.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class AddressGenerationViewModel @Inject constructor(
    private val generateAddressUseCase: GenerateAddressUseCase
) : BaseViewModel() {

    private val _generateAddressLiveData: MutableLiveData<Resource<AddressKeychainItem>> =
        MutableLiveData()
    val generateAddressLiveData: LiveData<Resource<AddressKeychainItem>> =
        _generateAddressLiveData

    fun generateAddress() = viewModelScope.launch(Dispatchers.IO) {

        _generateAddressLiveData.postLoading()

        runCatching {
            generateAddressUseCase.invoke(null)
        }.onSuccess {
            _generateAddressLiveData.postSuccess(it)
        }.onFailure {
            _generateAddressLiveData.postFailure(it.toFailure())
        }
    }
}
