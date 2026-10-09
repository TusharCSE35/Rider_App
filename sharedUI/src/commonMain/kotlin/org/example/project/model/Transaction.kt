package org.example.project.model

data class Transaction(
    val id: String,
    val title: String,
    val date: String,
    val amount: Int,
    val type: TransactionType
)