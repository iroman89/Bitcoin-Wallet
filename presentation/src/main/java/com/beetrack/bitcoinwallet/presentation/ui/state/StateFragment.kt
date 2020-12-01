package com.beetrack.bitcoinwallet.presentation.ui.state

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.beetrack.bitcointwallet.presentation.databinding.FragmentStateBinding
import com.beetrack.bitcoinwallet.presentation.appComponent
import com.beetrack.bitcoinwallet.presentation.util.BaseFragment

class StateFragment : BaseFragment<FragmentStateBinding>() {

    override fun setBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentStateBinding =
        FragmentStateBinding.inflate(layoutInflater, container, false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent().inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}