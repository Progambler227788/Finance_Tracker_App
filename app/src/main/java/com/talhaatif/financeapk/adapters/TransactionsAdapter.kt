package com.talhaatif.financeapk.adapter

import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.talhaatif.financeapk.R
import com.talhaatif.financeapk.databinding.RvTransactionsBinding
import com.talhaatif.financeapk.databinding.ItemTransactionDateBinding
import com.talhaatif.financeapk.firebase.Variables
import com.talhaatif.financeapk.models.Transaction

class TransactionsAdapter(private var groupedTransactions: Map<String, List<Transaction>>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val items = mutableListOf<Any>()

    init {
        updateItems()
    }

    companion object {
        private const val TYPE_DATE = 0
        private const val TYPE_TRANSACTION = 1
    }

    fun updateData(newData: Map<String, List<Transaction>>) {
        groupedTransactions = newData
        updateItems()
        notifyDataSetChanged()
    }

    private fun updateItems() {
        items.clear()
        for ((date, transactions) in groupedTransactions) {
            items.add(date) // Add date as a header
            items.addAll(transactions) // Add transactions under that date
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (items[position] is String) TYPE_DATE else TYPE_TRANSACTION
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == TYPE_DATE) {
            val binding = ItemTransactionDateBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            DateViewHolder(binding)
        } else {
            val binding = RvTransactionsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            TransactionViewHolder(binding)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is DateViewHolder) {
            holder.bind(items[position] as String)
        } else if (holder is TransactionViewHolder) {
            holder.bind(items[position] as Transaction)
        }
    }

    override fun getItemCount(): Int = items.size

    class DateViewHolder(private val binding: ItemTransactionDateBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(date: String) {
            binding.transactionDate.text = date
        }
    }

    class TransactionViewHolder(private val binding: RvTransactionsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(transaction: Transaction) {

            val tintColor = Variables.categoryTintMap[transaction.category] ?: Color.BLACK
            val categoryImage = Variables.categoryResourceMap[transaction.category] ?: R.drawable.ic_food

            binding.transactionView.setImageResource(categoryImage)
            binding.transactionView.setColorFilter(tintColor)


            // to capitalize the word like food -> Food
            binding.transactionType.text = transaction.category.replaceFirstChar { it.uppercase() }
            binding.transactionAmount.text = transaction.transAmount
            if (transaction.transType == "Income")
            {   binding.transactionAmount.setTextColor(Color.GREEN)
                binding.transactionAmount.text = "+"  + transaction.transAmount

            }
            else{
                binding.transactionAmount.setTextColor(Color.RED)
                binding.transactionAmount.text = "-" + transaction.transAmount

            }
            binding.transactionDate.text = transaction.transDate
        }
    }
}
