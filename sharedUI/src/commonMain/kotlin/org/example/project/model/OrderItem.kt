package org.example.project.model

data class OrderItem(
    val name: String,

    val qty: Int,
    val price: Int
) {
    val total get() = qty * price
}