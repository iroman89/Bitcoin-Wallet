package cl.iroman.bitcoinwallet.presentation.ui.address.historyTransaction.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import cl.iroman.bitcoinwallet.domain.model.AddressTransactionModel
import cl.iroman.bitcoinwallet.domain.useCase.GetTransactionsUseCase
import cl.iroman.bitcoinwallet.domain.util.Failure
import cl.iroman.bitcoinwallet.domain.util.toFailure
import cl.iroman.bitcoinwallet.presentation.util.BaseViewModel
import cl.iroman.bitcoinwallet.presentation.util.ResourceState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

class HistoryTransactionViewModel @Inject constructor(private val getTransactionsUseCase: GetTransactionsUseCase) :
    BaseViewModel() {

    private val _historyTransactionLiveData: MutableLiveData<ResourceState<AddressTransactionModel>> =
        MutableLiveData()
    val historyTransactionLiveData: LiveData<ResourceState<AddressTransactionModel>> =
        _historyTransactionLiveData

    fun getTransactions() {
        viewModelScope.launch {
            _historyTransactionLiveData.postLoading()

            runCatching {
                getTransactionsUseCase.invoke(null)
            }.onSuccess {
                if (it.transactions?.isEmpty() == true) {
                    delay(600)
                    _historyTransactionLiveData.postFailure(Failure.NoTransaction)
                } else {
                    _historyTransactionLiveData.postSuccess(it)
                }
            }.onFailure {
                when (it) {
                    is IllegalArgumentException -> {
                        delay(600)
                        _historyTransactionLiveData.postFailure(Failure.Empty)
                    }
                    else -> _historyTransactionLiveData.postFailure(it.toFailure())
                }
            }
        }
    }
}