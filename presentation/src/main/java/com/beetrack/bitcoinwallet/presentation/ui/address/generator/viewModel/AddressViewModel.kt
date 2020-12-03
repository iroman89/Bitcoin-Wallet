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
import com.beetrack.bitcoinwallet.presentation.util.SingleShotLiveData
import kotlinx.coroutines.delay
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
    val addressLiveData: LiveData<AddressState<AddressKeychainModel>>
        get() = _addressLiveData

    private val _saveAddressLiveData: SingleShotLiveData<SaveAddressState<Boolean>> =
        SingleShotLiveData()
    val saveAddressLiveData: LiveData<SaveAddressState<Boolean>>
        get() = _saveAddressLiveData

    private val currentAddress: AddressKeychainModel?
        get() {
            return _addressLiveData.value?.data
        }

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
                _saveAddressLiveData.postValue(SaveAddressState.Error(Failure.NoDataToSave))
                return@launch
            }
            _saveAddressLiveData.postValue(SaveAddressState.Loading())

            runCatching {
                saveAddressUseCase.invoke(data)
            }.onSuccess {
                _saveAddressLiveData.postValue(SaveAddressState.Saved())
            }.onFailure {
                _saveAddressLiveData.postValue(SaveAddressState.Error(it.toFailure()))
            }
        }
    }
}