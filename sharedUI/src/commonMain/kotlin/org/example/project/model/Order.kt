package org.example.project.model

data class Order(
    val id: String,
    val restaurant: String,
    val customer: String,
    val pickupAddress: String,
    val dropAddress: String,
    val items: Int,
    val distanceKm: Double,
    val earning: Int,
    val time: String,
    val status: OrderStatus,
    val itemList: List<OrderItem> = emptyList(),
    val paymentType: PaymentType = PaymentType.PAID,
    val collectAmount: Int = 0,
    val customerPhone: String = "",
    val restaurantPhone: String = "",
    val customerNote: String = "",
    val pickupLat: Double = 0.0,
    val pickupLng: Double = 0.0,
    val dropLat: Double = 0.0,
    val dropLng: Double = 0.0,
    val pickupCode: String = "",
    val deliveryOtp: String = ""
)