package org.example.project.model

import androidx.compose.runtime.mutableStateListOf

/**
 * In-memory session tracker for completed orders.
 *
 * - COD: rider collects [Order.collectAmount] from the customer. On completion only
 *   [Order.earning] + [Order.tip] are added to daily earnings; the collected cash
 *   goes to cash-in-hand / branch deposit.
 * - Online (bKash / Bank / Paid): no collection. Only [Order.earning] + [Order.tip]
 *   are added to daily earnings.
 */
object RiderSession {
    val completedOrders = mutableStateListOf<Order>()

    fun complete(order: Order) {
        completedOrders.add(order.copy(status = OrderStatus.DELIVERED))
    }

    fun clear() {
        completedOrders.clear()
    }

    /** Sum of delivery earnings + tips for completed orders in this session. */
    val sessionEarnings: Int get() = completedOrders.sumOf { it.riderPayout }

    val sessionTips: Int get() = completedOrders.sumOf { it.tip }

    val sessionDeliveries: Int get() = completedOrders.size

    /** Total COD cash collected from customers in this session. */
    val sessionCashCollected: Int
        get() = completedOrders.filter { it.paymentType.isCod }.sumOf { it.collectAmount }

    fun cashEntries(): List<CashEntry> = completedOrders
        .filter { it.paymentType.isCod }
        .map { CashEntry(it.id.removePrefix("#").let { "#$it" }, it.collectAmount, "Today") }
}
