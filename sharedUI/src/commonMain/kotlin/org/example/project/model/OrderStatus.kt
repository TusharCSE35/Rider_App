package org.example.project.model

enum class OrderStatus(val label: String) {
    ACCEPTED("Accepted"),
    PICKED_UP("Picked up"),
    ON_THE_WAY("On the way"),
    DELIVERED("Delivered"),
    CANCELLED("Cancelled");

    val isActive get() = this == ACCEPTED || this == PICKED_UP || this == ON_THE_WAY

    fun next(): OrderStatus? = when (this) {
        ACCEPTED -> PICKED_UP
        PICKED_UP -> ON_THE_WAY
        ON_THE_WAY -> DELIVERED
        else -> null
    }

    val actionLabel: String? get() = when (this) {
        ACCEPTED -> "Mark as picked up"
        PICKED_UP -> "Start delivery"
        ON_THE_WAY -> "Mark as delivered"
        else -> null
    }
}