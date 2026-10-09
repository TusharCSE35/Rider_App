package org.example.project.model

enum class CancelReason(val label: String) {
    CUSTOMER_UNREACHABLE("Customer unreachable"),
    WRONG_ADDRESS("Wrong / incomplete address"),
    RESTAURANT_CLOSED("Restaurant closed"),
    ORDER_NOT_READY("Order not ready for too long"),
    VEHICLE_ISSUE("Vehicle problem / accident"),
    OTHER("Other")
}