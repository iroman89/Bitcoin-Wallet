package cl.iroman.bitcoinwallet.presentation.ui.address.balance.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import cl.iroman.bitcoinwallet.domain.model.AddressBalanceModel
import cl.iroman.bitcoinwallet.domain.useCase.GetAddressBalanceUseCase
import cl.iroman.bitcoinwallet.domain.util.Failure
import cl.iroman.bitcoinwallet.domain.util.toFailure
import cl.iroman.bitcoinwallet.presentation.util.BaseViewModel
import cl.iroman.bitcoinwallet.presentation.util.ResourceState
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