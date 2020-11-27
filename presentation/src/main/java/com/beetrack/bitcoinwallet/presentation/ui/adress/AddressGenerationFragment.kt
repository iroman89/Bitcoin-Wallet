package com.beetrack.bitcoinwallet.presentation.ui.adress

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.beetrack.bitcointwallet.presentation.databinding.FragmentAddressBinding
import com.beetrack.bitcoinwallet.domain.model.address.AddressKeychainResponse
import com.beetrack.bitcoinwallet.domain.util.Failure
import com.beetrack.bitcoinwallet.presentation.appComponent
import com.beetrack.bitcoinwallet.presentation.util.BaseFragment
import com.beetrack.bitcoinwallet.presentation.util.subscribe

class AddressGenerationFragment : BaseFragment<FragmentAddressBinding>() {

    private val generationViewModel: AddressGenerationViewModel by viewModels { viewModelFactory }

    override fun setBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentAddressBinding = FragmentAddressBinding.inflate(inflater, container, false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent().inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        generationViewModel.generateAddressLiveData.subscribe(
            this@AddressGenerationFragment,
            ::showProgress,
            ::handleAddressGenerateSuccess,
            ::manageFailure
        )
    }

    private fun manageFailure(failure: Failure) {
        TODO("Not yet implemented")
    }

    private fun handleAddressGenerateSuccess(data: AddressKeychainResponse) {
        TODO("Not yet implemented")
    }

    private fun showProgress() {
        TODO("Not yet implemented")
    }
}