package cl.iroman.bitcoinwallet.presentation.ui.address.historyTransaction.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import cl.iroman.bitcointwallet.presentation.databinding.AdapterTransactionItemBinding
import cl.iroman.bitcoinwallet.domain.model.AddressTransactionModel
import cl.iroman.bitcoinwallet.presentation.util.DateFormat
import cl.iroman.bitcoinwallet.presentation.util.DecimalFormat

class TransactionAdapter constructor(private val items: List<AddressTransactionModel.TransactionItem>) :
    RecyclerView.Adapter<TransactionAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(AdapterTransactionItemBinding.inflate(LayoutInflater.from(parent.context),
            parent, false))
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val transaction = items[position]

        with(transaction) {
            holder.date.text = DateFormat.format(received, "dd-MM-yyyy")
            holder.total.text = DecimalFormat.format(total)
        }
    }

    override fun getItemCount(): Int = items.size

    class ViewHolder(binding: AdapterTransactionItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        val date = binding.dateValue
        val total = binding.amountValue
    }
}