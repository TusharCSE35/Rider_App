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
    val tip: Int = 0,
    val customerPhone: String = "",
    val restaurantPhone: String = "",
    val customerNote: String = "",
    val pickupLat: Double = 0.0,
    val pickupLng: Double = 0.0,
    val dropLat: Double = 0.0,
    val dropLng: Double = 0.0,
    val pickupCode: String = "",
    val deliveryOtp: String = ""
) {
    /** Amount the rider must collect from the customer. 0 for online-paid orders. */
    val customerPayable: Int get() = if (paymentType.isCod) collectAmount else 0

    /** Amount added to the rider's daily earnings on completion (delivery fee + tip). */
    val riderPayout: Int get() = earning + tip

    /** True when the rider must collect cash from the customer. */
    val requiresCollection: Boolean get() = paymentType.isCod && collectAmount > 0
}