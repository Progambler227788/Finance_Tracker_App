package com.talhaatif.financeapk.viewmodel

import android.app.Application
import android.content.Context
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FirebaseFirestore
import com.talhaatif.financeapk.firebase.Util

class AddTransactionViewModel(application: Application) : AndroidViewModel(application) {
    private val db = FirebaseFirestore.getInstance()
    private val utils = Util()
    val transactionState = MutableLiveData<Result<Boolean>>() // Observing transaction status

    fun addTransaction(context: Context, amount: String, transType: String, selectedDate: String, notes: String, category : String) {
        val userId = utils.getLocalData(context, "uid") ?: ""

        val transaction = hashMapOf(
            "uid" to userId,
            "transAmount" to amount,
            "transType" to transType,
            "transDate" to selectedDate,
            "notes" to notes,
            "category" to category
        )

        db.collection("transactions").add(transaction)
            .addOnSuccessListener {
                updateBudget(userId, transType, amount.split(" ")[0].toDouble())
                transactionState.value = Result.success(true) // Notify activity
            }
            .addOnFailureListener { e ->
                transactionState.value = Result.failure(e)
            }
    }

    private fun updateBudget(userId: String, transType: String, amount: Double) {
        val budgetRef = db.collection("budget").document(userId)

        db.runTransaction { transaction ->
            val snapshot = transaction.get(budgetRef)
            val balance = snapshot.getDouble("balance") ?: 0.0
            val income = snapshot.getDouble("income") ?: 0.0
            val expense = snapshot.getDouble("expense") ?: 0.0

            val newBalance = if (transType == "Income") balance + amount else balance - amount
            val newIncome = if (transType == "Income") income + amount else income
            val newExpense = if (transType == "Expense") expense + amount else expense

            transaction.update(budgetRef, "balance", newBalance)
            transaction.update(budgetRef, "income", newIncome)
            transaction.update(budgetRef, "expense", newExpense)
        }.addOnSuccessListener {
            transactionState.value = Result.success(true)
        }.addOnFailureListener { e ->
            transactionState.value = Result.failure(e)
        }
    }
}
