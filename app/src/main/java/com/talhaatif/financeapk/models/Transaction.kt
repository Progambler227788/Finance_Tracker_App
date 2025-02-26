package com.talhaatif.financeapk.models

data class Transaction(
    val uid: String = "",
    val transAmount: String = "",
    val transType: String = "",
    val transDate: String = "",
    val notes: String = "",
    val category: String = ""
)
