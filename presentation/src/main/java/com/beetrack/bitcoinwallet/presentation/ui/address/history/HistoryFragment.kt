package com.beetrack.bitcoinwallet.presentation.ui.address.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import com.beetrack.bitcointwallet.presentation.R
import com.beetrack.bitcointwallet.presentation.databinding.FragmentHistoryBinding
import com.beetrack.bitcoinwallet.domain.model.AddressTransactionModel
import com.beetrack.bitcoinwallet.domain.util.Failure
import com.beetrack.bitcoinwallet.presentation.appComponent
import com.beetrack.bitcoinwallet.presentation.ui.address.history.adapter.TransactionAdapter
import com.beetrack.bitcoinwallet.presentation.ui.address.history.viewModel.HistoryViewModel
import com.beetrack.bitcoinwallet.presentation.util.BaseFragment
import com.beetrack.bitcoinwallet.presentation.util.ResourceState
import com.beetrack.bitcoinwallet.presentation.util.extension.*

class HistoryFragment : BaseFragment<FragmentHistoryBinding>() {

    private val historyViewModel: HistoryViewModel by viewModels { viewModelFactory }

    override fun setBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ): FragmentHistoryBinding = FragmentHistoryBinding.inflate(inflater, container, false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent().inject(this)
        subscribe()
    }

    private fun subscribe() {
        observe(historyViewModel.historyTransactionLiveData) {
            handleHistoryTransactionState(it)
        }
    }

    private fun handleHistoryTransactionState(state: ResourceState<AddressTransactionModel>) {
        when (state) {
            is ResourceState.Loading -> showProgress()
            is ResourceState.Success -> historyTransactionSuccess(state.data)
            is ResourceState.Error -> manageFailure(state.failure)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().setToolbarTitle(getString(R.string.history_transactions))

        with(binding) {
            swipeRefresh.setOnRefreshListener {
                swipeRefresh.isRefreshing = false
                historyViewModel.getTransactions()
            }
        }

        historyViewModel.historyTransactionLiveData.value?.also {
            handleHistoryTransactionState(it)
        } ?: getTransactions()
    }

    private fun getTransactions() {
        if (!requireContext().hasNetworkAvailable()) {
            manageFailure(Failure.NetworkConnection)
            return
        }
        historyViewModel.getTransactions()
    }

    private fun historyTransactionSuccess(data: AddressTransactionModel?) {
        hideProgress {
            data?.transactions?.also {
                with(binding.historyList) {
                    adapter =
                        TransactionAdapter(it)
                    addItemDecoration(DividerItemDecoration(requireContext(),
                        DividerItemDecoration.VERTICAL))
                }
            }
        }
    }

    private fun manageFailure(failure: Failure?) =
        when (failure) {
            Failure.NoTransaction -> showEmpty(getString(R.string.no_transactions))
            Failure.Empty -> showEmpty(getString(R.string.not_address_saved))
            Failure.NetworkConnection -> requireActivity().toast(getString(R.string.network_error))
            else -> {
                requireActivity().toast(getString(R.string.generic_error))
            }
        }

    private fun showEmpty(message: String) =
        with(binding) {
            progressScreen.gone()
            historyList.gone()
            errorScreen.visible()
            errorScreen.errorButton.gone()
            errorScreen.errorMessage.text = message
        }

    private fun showProgress() {
        with(binding) {
            progressScreen.visible()
            progressScreen.progressMessage.text =
                getString(R.string.getting_historial_transaction)
            historyList.gone()
            errorScreen.gone()
        }
    }

    private fun hideProgress(func: () -> Unit) =
        try {
            func()
        } finally {
            with(binding) {
                swipeRefresh.isRefreshing = false
                progressScreen.gone()
                historyList.visible()
                errorScreen.gone()
            }
        }
}