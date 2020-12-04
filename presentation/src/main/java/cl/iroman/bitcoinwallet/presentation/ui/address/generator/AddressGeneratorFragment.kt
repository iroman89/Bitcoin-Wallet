package cl.iroman.bitcoinwallet.presentation.ui.address.generator

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.viewModels
import cl.iroman.bitcointwallet.presentation.R
import cl.iroman.bitcointwallet.presentation.databinding.FragmentAddressBinding
import cl.iroman.bitcoinwallet.domain.model.AddressKeychainModel
import cl.iroman.bitcoinwallet.domain.util.Failure
import cl.iroman.bitcoinwallet.presentation.appComponent
import cl.iroman.bitcoinwallet.presentation.ui.address.generator.viewModel.AddressState
import cl.iroman.bitcoinwallet.presentation.ui.address.generator.viewModel.AddressViewModel
import cl.iroman.bitcoinwallet.presentation.ui.address.generator.viewModel.SaveAddressState
import cl.iroman.bitcoinwallet.presentation.util.BaseFragment
import cl.iroman.bitcoinwallet.presentation.util.extension.*
import cl.iroman.bitcoinwallet.presentation.util.toBitmapQR

class AddressGeneratorFragment : BaseFragment<FragmentAddressBinding>() {

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
            observe(addressLiveData) {
                handleAddressState(it)
            }
            observe(saveAddressLiveData) {
                handleSaveAddressState(it)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().setToolbarTitle(getString(R.string.address_generator))

        with(binding) {
            val generateAddressClickListener = View.OnClickListener {
                if (!requireContext().hasNetworkAvailable()) {
                    manageFailure(Failure.NetworkConnection)
                    return@OnClickListener
                }
                addressViewModel.generateAddress()
            }

            addressValue.setOnClickListener {
                requireContext().copyInClipboard("Address", (it as TextView).text.toString())
            }

            newAddress.setOnClickListener(generateAddressClickListener)
            errorScreen.errorButton.setOnClickListener(generateAddressClickListener)

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

        addressViewModel.addressLiveData.value?.also {
            handleAddressState(it)
        } ?: addressViewModel.getAddress()
    }

    private fun handleSaveAddressState(state: SaveAddressState<Boolean>) =
        when (state) {
            is SaveAddressState.Loading -> showProgress()
            is SaveAddressState.Saved -> saveAddressSuccess()
            is SaveAddressState.Error -> manageFailure(state.failure)
        }

    private fun handleAddressState(state: AddressState<AddressKeychainModel>) =
        when (state) {
            is AddressState.Loading -> showProgress()
            is AddressState.Got -> getOrGenerateAddressSuccess(state.data)
            is AddressState.Generated -> getOrGenerateAddressSuccess(state.data, true)
            is AddressState.Error -> manageFailure(state.failure)
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

    private fun saveAddressSuccess() {
        hideProgress {
            binding.saveAddress.isEnabled = false
            requireActivity().toast(getString(R.string.address_saved))
        }
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
            progressScreen.progressMessage.text = message
            normalView.gone()
            errorScreen.gone()
            progressScreen.visible()
        }

    private fun showEmpty() =
        with(binding) {
            progressScreen.gone()
            normalView.gone()
            errorScreen.errorMessage.text = getString(R.string.not_address_saved)
            errorScreen.visible()
        }

    private fun hideProgress(func: () -> Unit) =
        try {
            func()
        } finally {
            with(binding) {
                progressScreen.gone()
                errorScreen.gone()
                normalView.visible()
            }
        }
}