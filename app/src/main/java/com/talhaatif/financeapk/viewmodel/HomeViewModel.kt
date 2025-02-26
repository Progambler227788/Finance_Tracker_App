package com.talhaatif.financeapk.viewmodel

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.talhaatif.financeapk.firebase.Util
import com.talhaatif.financeapk.firebase.Variables
import com.talhaatif.financeapk.models.BudgetModel

class HomeViewModel(application: Application) : AndroidViewModel(application) {

    private val auth: FirebaseAuth = Variables.auth
    private val firestore: FirebaseFirestore = Variables.db
    private val utils = Util()

    private val _budgetData = MutableLiveData<BudgetModel?>()
    val budgetData: LiveData<BudgetModel?> get() = _budgetData

    private val _currencyType = MutableLiveData<String>()
    val currencyType: LiveData<String> get() = _currencyType

    init {
        fetchBudgetData()
        fetchUserCurrencyType()
    }

    private fun fetchBudgetData() {
        val uid = auth.currentUser?.uid ?: return
        firestore.collection("budget").document(uid)
            .addSnapshotListener { document, error ->
                if (error != null) {
                    Log.e("HomeViewModel", "Error fetching budget data", error)
                    return@addSnapshotListener
                }

                val budget = document?.toObject(BudgetModel::class.java)
                _budgetData.postValue(budget)
            }
    }

    private fun fetchUserCurrencyType() {
        val currency = utils.getLocalData(getApplication(), "currency") ?: "USD"
        Log.d("HomeViewModel", "Currency -> $currency")
        _currencyType.postValue(currency)


    }
}
