package com.beetrack.bitcoinwallet.presentation.ui.address.historyTransaction.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.beetrack.bitcoinwallet.domain.model.AddressTransactionModel
import com.beetrack.bitcoinwallet.domain.useCase.GetTransactionsUseCase
import com.beetrack.bitcoinwallet.domain.util.Failure
import com.beetrack.bitcoinwallet.domain.util.toFailure
import com.beetrack.bitcoinwallet.presentation.util.BaseViewModel
import com.beetrack.bitcoinwallet.presentation.util.ResourceState
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