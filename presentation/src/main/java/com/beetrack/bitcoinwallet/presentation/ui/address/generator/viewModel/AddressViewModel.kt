package com.beetrack.bitcoinwallet.presentation.ui.address.generator.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.beetrack.bitcoinwallet.domain.model.AddressKeychainModel
import com.beetrack.bitcoinwallet.domain.useCase.GenerateAddressUseCase
import com.beetrack.bitcoinwallet.domain.useCase.GetAddressUseCase
import com.beetrack.bitcoinwallet.domain.useCase.SaveAddressUseCase
import com.beetrack.bitcoinwallet.domain.util.Failure
import com.beetrack.bitcoinwallet.domain.util.toFailure
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class AddressViewModel @Inject constructor(
    private val getAddressUseCase: GetAddressUseCase,
    private val generateAddressUseCase: GenerateAddressUseCase,
    private val saveAddressUseCase: SaveAddressUseCase,
) : ViewModel() {

    private val _addressLiveData: MutableLiveData<AddressState<AddressKeychainModel>> =
        MutableLiveData()
    val addressLiveData: LiveData<AddressState<AddressKeychainModel>> =
        _addressLiveData

    private val currentAddress: AddressKeychainModel? =
        _addressLiveData.value?.data

    fun getAddress() {
        viewModelScope.launch {
            _addressLiveData.postValue(AddressState.Loading())

            runCatching {
                getAddressUseCase.invoke(null)
            }.onSuccess {
                it.catch {
                    _addressLiveData.postValue(AddressState.Error(Failure.Empty))
                }.collect { address ->
                    _addressLiveData.postValue(AddressState.Got(address))
                }
            }.onFailure {
                _addressLiveData.postValue(AddressState.Error(it.toFailure()))
            }
        }
    }

    fun generateAddress() {
        viewModelScope.launch {
            _addressLiveData.postValue(AddressState.Loading())

            runCatching {
                generateAddressUseCase.invoke(null)
            }.onSuccess {
                _addressLiveData.postValue(AddressState.Generated(it))
            }.onFailure {
                _addressLiveData.postValue(AddressState.Error(it.toFailure()))
            }
        }
    }

    fun saveAddress() {
        viewModelScope.launch {

            val data = currentAddress
            if (data == null) {
                _addressLiveData.postValue(AddressState.Error(Failure.NoDataToSave))
                return@launch
            }
            _addressLiveData.postValue(AddressState.Loading())

            runCatching {
                saveAddressUseCase.invoke(data)
            }.onSuccess {
                _addressLiveData.postValue(AddressState.Saved())
            }.onFailure {
                _addressLiveData.postValue(AddressState.Error(it.toFailure()))
            }
        }
    }
}