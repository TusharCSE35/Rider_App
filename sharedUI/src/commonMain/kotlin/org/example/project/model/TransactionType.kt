package org.example.project.model

enum class TransactionType(val label: String) {
    EARNING("Earning"),
    TIP("Tip"),
    BONUS("Bonus"),
    PENALTY("Penalty"),
    PAYOUT("Payout")
}