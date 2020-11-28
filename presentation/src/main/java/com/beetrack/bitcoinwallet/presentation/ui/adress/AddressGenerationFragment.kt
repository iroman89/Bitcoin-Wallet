package com.beetrack.bitcoinwallet.presentation.ui.adress

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.beetrack.bitcointwallet.presentation.databinding.FragmentAddressBinding
import com.beetrack.bitcoinwallet.domain.model.address.AddressKeychainItem
import com.beetrack.bitcoinwallet.domain.util.Failure
import com.beetrack.bitcoinwallet.presentation.appComponent
import com.beetrack.bitcoinwallet.presentation.util.BaseFragment
import com.beetrack.bitcoinwallet.presentation.util.extension.gone
import com.beetrack.bitcoinwallet.presentation.util.extension.visible
import com.beetrack.bitcoinwallet.presentation.util.subscribe
import com.beetrack.bitcoinwallet.presentation.util.toBitmapQR


class AddressGenerationFragment : BaseFragment<FragmentAddressBinding>() {

    private val generationViewModel: AddressGenerationViewModel by viewModels { viewModelFactory }

    override fun setBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentAddressBinding = FragmentAddressBinding.inflate(inflater, container, false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent().inject(this)
        setupViewModel()
    }

    private fun setupViewModel() {
        generationViewModel.generateAddressLiveData.subscribe(
            this@AddressGenerationFragment,
            ::showProgress,
            ::handleAddressGenerateSuccess,
            ::manageFailure
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.newAddress.setOnClickListener {
            generationViewModel.generateAddress()
        }
        binding.saveAddress.setOnClickListener {
//            requireContext().showMessageOKCancel()
        }
    }

    private fun manageFailure(failure: Failure) {
        hideProgress {
            //Todo
        }
    }

    private fun handleAddressGenerateSuccess(data: AddressKeychainItem) {
        hideProgress {
            binding.addressValue.text = data.address
            binding.addressQr.setImageBitmap(data.address?.toBitmapQR())
        }
    }

    private fun showProgress() {
        binding.progressScreen.visible()
        binding.progressScreen.progressMessage.text = "Generating Address..."
        binding.visibleGroup.gone()
    }

    private fun hideProgress(func: () -> Unit) {
        try {
            func()
        } finally {
            binding.progressScreen.gone()
            binding.visibleGroup.visible()
        }
    }
}