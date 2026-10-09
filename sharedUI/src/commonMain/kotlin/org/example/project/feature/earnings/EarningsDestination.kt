package org.example.project.feature.earnings

enum class EarningsDestination(val label: String, val emoji: String) {
    CASH("Cash on hand", "\uD83D\uDCB5"),
    WITHDRAW("Withdraw", "\uD83C\uDFE6"),
    TRANSACTIONS("Transactions", "\uD83D\uDCCB"),
    INCENTIVES("Incentives & bonus", "\uD83C\uDFAF"),
    TIPS("Tips", "\uD83D\uDCB8")
}