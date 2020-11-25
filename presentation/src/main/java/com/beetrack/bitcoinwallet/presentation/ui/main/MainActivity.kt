package com.beetrack.bitcoinwallet.presentation.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.beetrack.bitcointwallet.presentation.R
import com.beetrack.bitcointwallet.presentation.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        setupNavigation()
    }

    private fun setupNavigation() {
        val navController = Navigation.findNavController(this, R.id.nav_main_host_fragment)
        NavigationUI.setupWithNavController(binding.mainBottomNav, navController)
    }
}