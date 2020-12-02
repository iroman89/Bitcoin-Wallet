package com.beetrack.bitcoinwallet.presentation.ui.address.history.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.beetrack.bitcointwallet.presentation.databinding.AdapterTransactionItemBinding
import com.beetrack.bitcoinwallet.domain.model.AddressTransactionModel
import com.beetrack.bitcoinwallet.presentation.util.DateFormat

class TransactionAdapter constructor(private val items: List<AddressTransactionModel.TransactionItem>) :
    RecyclerView.Adapter<TransactionAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(AdapterTransactionItemBinding.inflate(LayoutInflater.from(parent.context),
            parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val transaction = items[position]

        with(transaction) {
            holder.date.text = DateFormat.format(received, "dd-MM-yyyy")
            holder.amount.text = total.toString()
        }
    }

    override fun getItemCount(): Int = items.size

    class ViewHolder(binding: AdapterTransactionItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        val date = binding.date
        val amount = binding.amount
    }
}