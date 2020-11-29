package com.beetrack.bitcoinwallet.presentation.ui.address

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.beetrack.bitcointwallet.presentation.R
import com.beetrack.bitcointwallet.presentation.databinding.FragmentAddressBinding
import com.beetrack.bitcoinwallet.domain.model.address.AddressKeychainModel
import com.beetrack.bitcoinwallet.domain.util.Failure
import com.beetrack.bitcoinwallet.presentation.appComponent
import com.beetrack.bitcoinwallet.presentation.util.BaseFragment
import com.beetrack.bitcoinwallet.presentation.util.extension.gone
import com.beetrack.bitcoinwallet.presentation.util.extension.hasNetworkAvailable
import com.beetrack.bitcoinwallet.presentation.util.extension.toast
import com.beetrack.bitcoinwallet.presentation.util.extension.visible
import com.beetrack.bitcoinwallet.presentation.util.subscribe
import com.beetrack.bitcoinwallet.presentation.util.toBitmapQR


class AddressGenerationFragment : BaseFragment<FragmentAddressBinding>() {

    private val generationViewModel: AddressGenerationViewModel by viewModels { viewModelFactory }

    override fun setBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ): FragmentAddressBinding = FragmentAddressBinding.inflate(inflater, container, false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent().inject(this)
        subscribe()
    }

    private fun subscribe() {
        generationViewModel.generateAddressLiveData.subscribe(
            this@AddressGenerationFragment,
            ::showProgress,
            ::handleAddressGenerateSuccess,
            ::manageFailure
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            newAddress.setOnClickListener {
                getAddress(true)
            }
            saveAddress.setOnClickListener {
//            requireContext().showMessageOKCancel()
            }
            emptyScreen.emptyStart.setOnClickListener {
                getAddress(true)
            }
        }
        getAddress()
    }

    private fun getAddress(generateNew: Boolean = false) {
        if (!requireContext().hasNetworkAvailable()) {
            manageFailure(Failure.NetworkConnection)
            return
        }
        generationViewModel.getAddress(generateNew)
    }

    private fun manageFailure(failure: Failure) {
        hideProgress {
            when (failure) {
                Failure.Empty -> showEmpty()
                Failure.NetworkConnection -> requireActivity().toast(getString(R.string.network_error))
                else -> {
                    requireActivity().toast(getString(R.string.generic_error))
                }
            }
        }
    }

    private fun handleAddressGenerateSuccess(data: AddressKeychainModel) {
        hideProgress {
            with(binding) {
                addressValue.text = data.address
                addressQr.setImageBitmap(data.address?.toBitmapQR())
            }
        }
    }

    private fun showProgress() {
        with(binding) {
            progressScreen.visible()
            progressScreen.progressMessage.text = "Generating Address..."
            normalView.gone()
        }
    }

    private fun hideProgress(func: () -> Unit) {
        try {
            func()
        } finally {
            with(binding) {
                progressScreen.gone()
                normalView.visible()
            }
        }
    }

    private fun showEmpty() {
        with(binding) {
            progressScreen.gone()
            normalView.gone()
            emptyScreen.visible()
        }
    }
}