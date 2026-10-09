package org.example.project.model

enum class WithdrawMethod(val label: String, val hint: String) {
    BKASH("bKash", "bKash number (01XXXXXXXXX)"),
    NAGAD("Nagad", "Nagad number (01XXXXXXXXX)"),
    BANK("Bank", "Bank account number")
}