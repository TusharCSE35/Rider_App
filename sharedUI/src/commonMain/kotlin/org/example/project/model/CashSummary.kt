package org.example.project.model

data class CashEntry(
    val orderId: String,

    val amount: Int,
    val time: String
)

data class CashSummary(
    val cashInHand: Int,
    val limit: Int,
    val entries: List<CashEntry>
)