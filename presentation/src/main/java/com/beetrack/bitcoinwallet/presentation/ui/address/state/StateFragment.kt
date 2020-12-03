package com.beetrack.bitcoinwallet.presentation.ui.address.state

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.beetrack.bitcointwallet.presentation.R
import com.beetrack.bitcointwallet.presentation.databinding.FragmentStateBinding
import com.beetrack.bitcoinwallet.domain.model.AddressBalanceModel
import com.beetrack.bitcoinwallet.domain.util.Failure
import com.beetrack.bitcoinwallet.presentation.appComponent
import com.beetrack.bitcoinwallet.presentation.ui.address.state.viewModel.StateViewModel
import com.beetrack.bitcoinwallet.presentation.util.BaseFragment
import com.beetrack.bitcoinwallet.presentation.util.DecimalFormat
import com.beetrack.bitcoinwallet.presentation.util.ResourceState
import com.beetrack.bitcoinwallet.presentation.util.extension.*
import com.beetrack.bitcoinwallet.presentation.util.toBitmapQR

class StateFragment : BaseFragment<FragmentStateBinding>() {

    private val stateViewModel: StateViewModel by viewModels { viewModelFactory }

    override fun setBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentStateBinding =
        FragmentStateBinding.inflate(layoutInflater, container, false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent().inject(this)
        subscribe()
    }

    private fun subscribe() {
        observe(stateViewModel.addressBalanceLiveData) {
            handleAddressBalanceState(it)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().setToolbarTitle(getString(R.string.wallet_state))

        with(binding) {
            swipeRefresh.setOnRefreshListener {
                swipeRefresh.isRefreshing = false
                stateViewModel.getAddressBalance()
            }
        }

        stateViewModel.addressBalanceLiveData.value?.also {
            handleAddressBalanceState(it)
        } ?: getAddressBalance()
    }

    private fun getAddressBalance() {
        if (!requireContext().hasNetworkAvailable()) {
            manageFailure(Failure.NetworkConnection)
            return
        }
        stateViewModel.getAddressBalance()
    }

    private fun handleAddressBalanceState(state: ResourceState<AddressBalanceModel>) {
        when (state) {
            is ResourceState.Loading -> showProgress()
            is ResourceState.Success -> addressBalanceSuccess(state.data)
            is ResourceState.Error -> manageFailure(state.failure)
        }
    }

    private fun addressBalanceSuccess(data: AddressBalanceModel?) =
        hideProgress {
            with(binding) {
                addressValue.text = data?.address
                addressQr.setImageBitmap(data?.address?.toBitmapQR(200, 200))
                balanceValue.text = DecimalFormat.format(data?.balance)
                unconfirmedBalanceValue.text = DecimalFormat.format(data?.unconfirmedBalance)
                finalBalanceValue.text = DecimalFormat.format(data?.finalBalance)
            }
        }

    private fun manageFailure(failure: Failure?) =
        when (failure) {
            Failure.Empty -> showEmpty()
            Failure.NetworkConnection -> requireActivity().toast(getString(R.string.network_error))
            else -> showError()
        }

    private fun showError() {
        with(binding) {
            progressScreen.gone()
            normalView.gone()
            errorScreen?.errorMessage?.text = getString(R.string.generic_error)
            errorScreen?.errorButton?.gone()
            errorScreen?.visible()
        }
    }

    private fun showEmpty() =
        with(binding) {
            progressScreen.gone()
            normalView.gone()
            errorScreen?.errorMessage?.text = getString(R.string.not_address_saved)
            errorScreen?.errorButton?.gone()
            errorScreen?.visible()
        }

    private fun showProgress() {
        with(binding) {
            progressScreen.visible()
            progressScreen.progressMessage.text =
                getString(R.string.getting_address_balance_progress)
            normalView.gone()
        }
    }

    private fun hideProgress(func: () -> Unit) =
        try {
            func()
        } finally {
            with(binding) {
                swipeRefresh.isRefreshing = false
                progressScreen.gone()
                errorScreen?.gone()
                normalView.visible()
            }
        }
}