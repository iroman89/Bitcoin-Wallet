package com.beetrack.bitcoinwallet.presentation.ui.address.history.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.beetrack.bitcoinwallet.domain.model.AddressTransactionModel
import com.beetrack.bitcoinwallet.domain.useCase.GetTransactionsUseCase
import com.beetrack.bitcoinwallet.domain.util.Failure
import com.beetrack.bitcoinwallet.domain.util.toFailure
import com.beetrack.bitcoinwallet.presentation.util.BaseViewModel
import com.beetrack.bitcoinwallet.presentation.util.ResourceState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class HistoryViewModel @Inject constructor(private val getTransactionsUseCase: GetTransactionsUseCase) :
    BaseViewModel() {

    private val _historyTransactionLiveData: MutableLiveData<ResourceState<AddressTransactionModel>> =
        MutableLiveData()
    val historyTransactionLiveData: LiveData<ResourceState<AddressTransactionModel>> =
        _historyTransactionLiveData

    fun getTransactions() {
        viewModelScope.launch(Dispatchers.IO) {
            _historyTransactionLiveData.postLoading()

            runCatching {
                getTransactionsUseCase.invoke(null)
            }.onSuccess {
                if (it.transactions?.isEmpty() == true) {
                    _historyTransactionLiveData.postFailure(Failure.Empty)
                } else {
                    _historyTransactionLiveData.postSuccess(it)
                }
            }.onFailure {
                _historyTransactionLiveData.postFailure(it.toFailure())
            }
        }
    }
}