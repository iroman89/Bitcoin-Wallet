package com.beetrack.bitcoinwallet.presentation.ui.address.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.beetrack.bitcoinwallet.domain.model.address.AddressKeychainModel
import com.beetrack.bitcoinwallet.domain.useCase.GenerateAddressUseCase
import com.beetrack.bitcoinwallet.domain.useCase.GetAddressUseCase
import com.beetrack.bitcoinwallet.domain.useCase.SaveAddressUseCase
import com.beetrack.bitcoinwallet.domain.util.Failure
import com.beetrack.bitcoinwallet.domain.util.toFailure
import com.beetrack.bitcoinwallet.presentation.util.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class AddressViewModel @Inject constructor(
    private val getAddressUseCase: GetAddressUseCase,
    private val generateAddressUseCase: GenerateAddressUseCase,
    private val saveAddressUseCase: SaveAddressUseCase,
) : BaseViewModel() {

    private val _getAddressLiveData: MutableLiveData<AddressState<AddressKeychainModel>> =
        MutableLiveData()
    val getAddressLiveData: LiveData<AddressState<AddressKeychainModel>> =
        _getAddressLiveData

    fun getAddress() {
        viewModelScope.launch(Dispatchers.IO) {
            _getAddressLiveData.postValue(AddressState.Loading())

            runCatching {
                getAddressUseCase.invoke(null)
            }.onSuccess {
                it.catch {
                    _getAddressLiveData.postValue(AddressState.Error(Failure.Empty))
                }.collect { address ->
                    _getAddressLiveData.postValue(AddressState.Got(address))
                }
            }.onFailure {
                _getAddressLiveData.postValue(AddressState.Error(it.toFailure()))
            }
        }
    }

    fun generateAddress() {
        viewModelScope.launch(Dispatchers.IO) {
            _getAddressLiveData.postValue(AddressState.Loading())

            runCatching {
                generateAddressUseCase.invoke(null)
            }.onSuccess {
                _getAddressLiveData.postValue(AddressState.Generated(it))
            }.onFailure {
                _getAddressLiveData.postValue(AddressState.Error(it.toFailure()))
            }
        }
    }

    fun saveAddress() {
        viewModelScope.launch(Dispatchers.IO) {
            _getAddressLiveData.postValue(AddressState.Loading())

            val data = _getAddressLiveData.value?.data
            if (data == null) {
                _getAddressLiveData.postValue(AddressState.Error(Failure.NoDataToSave))
                return@launch
            }

            runCatching {
                saveAddressUseCase.invoke(data)
            }.onSuccess {
                _getAddressLiveData.postValue(AddressState.Saved())
            }.onFailure {
                _getAddressLiveData.postValue(AddressState.Error(it.toFailure()))
            }
        }
    }
}