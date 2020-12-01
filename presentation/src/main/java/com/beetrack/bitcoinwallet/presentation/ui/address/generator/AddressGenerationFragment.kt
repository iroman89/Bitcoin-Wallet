package com.beetrack.bitcoinwallet.presentation.ui.address.generator

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
import com.beetrack.bitcoinwallet.presentation.ui.address.generator.viewModel.AddressState
import com.beetrack.bitcoinwallet.presentation.ui.address.generator.viewModel.AddressViewModel
import com.beetrack.bitcoinwallet.presentation.util.BaseFragment
import com.beetrack.bitcoinwallet.presentation.util.extension.*
import com.beetrack.bitcoinwallet.presentation.util.toBitmapQR

class AddressGenerationFragment : BaseFragment<FragmentAddressBinding>() {

    private val addressViewModel: AddressViewModel by viewModels { viewModelFactory }

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
        with(addressViewModel) {
            observe(getAddressLiveData) {
                it?.also {
                    handleAddressState(it)
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            val generateAddressClickListener = View.OnClickListener {
                if (!requireContext().hasNetworkAvailable()) {
                    manageFailure(Failure.NetworkConnection)
                    return@OnClickListener
                }
                addressViewModel.generateAddress()
            }

            newAddress.setOnClickListener(generateAddressClickListener)
            emptyScreen.emptyStart.setOnClickListener(generateAddressClickListener)

            saveAddress.isEnabled = false
            saveAddress.setOnClickListener {
                if (!it.isEnabled) {
                    requireActivity().toast(getString(R.string.no_address_to_save))
                    return@setOnClickListener
                }
                requireContext().showMessageOKCancel(
                    getString(R.string.confirm),
                    getString(R.string.want_to_save_address),
                    getString(R.string.ok),
                    { dialog, _ ->
                        addressViewModel.saveAddress()
                        dialog.dismiss()
                    },
                    getString(R.string.cancel),
                    { dialog, _ ->
                        dialog.dismiss()
                    })
            }
        }

        addressViewModel.getAddressLiveData.value?.also {
            handleAddressState(it)
        } ?: addressViewModel.getAddress()
    }

    private fun handleAddressState(addressState: AddressState<AddressKeychainModel>) =
        when (addressState) {
            is AddressState.Loading -> showProgress()
            is AddressState.Got -> getOrGenerateAddressSuccess(addressState.data)
            is AddressState.Generated -> getOrGenerateAddressSuccess(addressState.data, true)
            is AddressState.Saved -> saveAddressSuccess()
            is AddressState.Error -> manageFailure(addressState.failure)
        }

    private fun getOrGenerateAddressSuccess(
        data: AddressKeychainModel?,
        isGenerated: Boolean = false,
    ) =
        hideProgress {
            with(binding) {
                saveAddress.isEnabled = isGenerated
                addressValue.text = data?.address
                addressQr.setImageBitmap(data?.address?.toBitmapQR())
            }
        }

    private fun saveAddressSuccess() =
        hideProgress {
            binding.saveAddress.isEnabled = false
            requireActivity().toast(getString(R.string.address_saved))
        }

    private fun manageFailure(failure: Failure?) =
        when (failure) {
            Failure.Empty -> showEmpty()
            Failure.NetworkConnection -> requireActivity().toast(getString(R.string.network_error))
            Failure.NoDataToSave -> requireActivity().toast(getString(R.string.no_address_to_save))
            else -> {
                requireActivity().toast(getString(R.string.generic_error))
            }
        }

    private fun showProgress(message: String = getString(R.string.generic_progress)) =
        with(binding) {
            progressScreen.visible()
            progressScreen.progressMessage.text = message
            normalView.gone()
            emptyScreen.gone()
        }

    private fun showEmpty() =
        with(binding) {
            progressScreen.gone()
            normalView.gone()
            emptyScreen.visible()
        }

    private fun hideProgress(func: () -> Unit) =
        try {
            func()
        } finally {
            with(binding) {
                progressScreen.gone()
                emptyScreen.gone()
                normalView.visible()
            }
        }
}