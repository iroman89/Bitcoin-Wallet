package com.beetrack.bitcoinwallet.presentation.ui.state

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.beetrack.bitcointwallet.presentation.R
import com.beetrack.bitcointwallet.presentation.databinding.FragmentStateBinding
import com.beetrack.bitcoinwallet.domain.model.addressBalance.AddressBalanceModel
import com.beetrack.bitcoinwallet.domain.util.Failure
import com.beetrack.bitcoinwallet.presentation.appComponent
import com.beetrack.bitcoinwallet.presentation.ui.state.viewModel.StateViewModel
import com.beetrack.bitcoinwallet.presentation.util.BaseFragment
import com.beetrack.bitcoinwallet.presentation.util.ResourceState
import com.beetrack.bitcoinwallet.presentation.util.extension.toast
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
            it?.also {
                handleAddressBalanceState(it)
            }
        }
    }

    private fun handleAddressBalanceState(state: ResourceState<AddressBalanceModel>) {
        when (state) {
            is ResourceState.Loading -> showProgress()
            is ResourceState.Success -> addressBalanceSuccess(state.data)
            is ResourceState.Error -> manageFailure(state.failure)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        stateViewModel.getAddressBalance()
    }

    private fun addressBalanceSuccess(data: AddressBalanceModel?) {
        with(binding) {
            addressValue.text = data?.address
            addressQr.setImageBitmap(data?.address?.toBitmapQR())
            balanceValue.text = data?.balance?.toString()
            unconfirmedBalanceValue.text = data?.unconfirmedBalance?.toString()
            finalBalanceValue.text = data?.finalBalance?.toString()
        }
    }

    private fun manageFailure(failure: Failure?) {
        when (failure) {
//            Failure.Empty -> showEmpty()
            Failure.NetworkConnection -> requireActivity().toast(getString(R.string.network_error))
            Failure.NoDataToSave -> requireActivity().toast(getString(R.string.no_address_to_save))
            else -> {
                requireActivity().toast(getString(R.string.generic_error))
            }
        }
    }

    private fun showProgress() {

    }
}