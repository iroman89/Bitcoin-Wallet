package com.beetrack.bitcoinwallet.presentation.util

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.viewbinding.ViewBinding
import javax.inject.Inject

abstract class BaseFragment<T : ViewBinding> : Fragment() {

    private var _binding: T? = null
    protected val binding: T by lazy<T> { _binding!! }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    abstract fun setBinding(inflater: LayoutInflater, container: ViewGroup?): T

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = setBinding(inflater, container)
        return binding.root
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    fun <T> Fragment.observe(liveData: LiveData<T>, observer: Observer<T>) {
        liveData.observe(this) {
            observer.onChanged(it)
        }
    }
}