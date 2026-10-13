package org.example.project.model

enum class PaymentType(val label: String) {
    COD("Cash on delivery"),
    BKASH("bKash"),
    BANK("Bank / Card"),
    PAID("Paid online");

    val isCod: Boolean get() = this == COD
    val isOnline: Boolean get() = this != COD
}