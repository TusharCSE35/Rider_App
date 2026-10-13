package org.example.project.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.example.project.model.Order
import org.example.project.model.OrderStatus
import org.example.project.model.PaymentType
import org.example.project.theme.*

@Composable
fun statusColor(status: OrderStatus): Color = when (status) {
    OrderStatus.DELIVERED -> Green
    OrderStatus.CANCELLED -> Color(0xFFD32F2F)
    else -> Orange
}

@Composable
fun OrderCard(
    order: Order,
    modifier: Modifier = Modifier,
    onAction: (() -> Unit)? = null,
    onClick: (() -> Unit)? = null
) {
    val color = statusColor(order.status)
    AppCard(
        shape = RoundedCornerShape(20.dp),
        modifier = modifier.fillMaxWidth()
    ) {
        Column(
            Modifier
                .then(if (onClick != null) Modifier.clickable(onClick = onClick) else Modifier)
                .padding(16.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Column(Modifier.weight(1f)) {
                    Text("\uD83C\uDF54 ${order.restaurant}", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                    Text("${order.id}  \u2022  ${order.time}", color = GreyText, fontSize = 12.sp)
                }
                Text(
                    order.status.label,
                    color = color,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier
                        .clip(RoundedCornerShape(50))
                        .background(color.copy(alpha = 0.12f))
                        .padding(horizontal = 10.dp, vertical = 4.dp)
                )
            }

            Spacer(Modifier.height(12.dp))
            AddressRow("🏪", "Pickup", order.pickupAddress)
            Spacer(Modifier.height(8.dp))
            AddressRow("📍", "Drop • ${order.customer}", order.dropAddress)

            Spacer(Modifier.height(12.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(
                        if (order.paymentType.isCod) Orange.copy(alpha = 0.10f)
                        else Green.copy(alpha = 0.10f)
                    )
                    .padding(horizontal = 12.dp, vertical = 10.dp)
            ) {
                StatusChip(
                    order.paymentType.label,
                    if (order.paymentType.isCod) Orange else Green
                )
                Spacer(Modifier.width(10.dp))
                Column(Modifier.weight(1f)) {
                    if (order.paymentType.isCod) {
                        Text(
                            "Collect ৳ ${order.collectAmount}",
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp
                        )
                        Text("Cash on delivery", color = GreyText, fontSize = 11.sp)
                    } else {
                        Text(
                            "No collection needed",
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 13.sp
                        )
                        Text("Paid ${order.paymentType.label}", color = GreyText, fontSize = 11.sp)
                    }
                }
                Column(horizontalAlignment = Alignment.End) {
                    Text("You earn", color = GreyText, fontSize = 11.sp)
                    Text(
                        "৳ ${order.earning}",
                        color = Green,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            HorizontalDivider(Modifier.padding(vertical = 12.dp))

            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Meta("Distance", "${order.distanceKm} km")
                Meta("Items", "${order.items}")
                Meta("You earn", "৳ ${order.earning}", valueColor = Green)
            }

            val label = order.status.actionLabel
            if (onAction != null && label != null) {
                Spacer(Modifier.height(14.dp))
                Button(
                    onClick = onAction,
                    shape = RoundedCornerShape(14.dp),
                    modifier = Modifier.fillMaxWidth().height(48.dp)
                ) { Text(label, fontWeight = FontWeight.SemiBold) }
            }
        }
    }
}

@Composable
private fun AddressRow(emoji: String, title: String, address: String) {
    Row(verticalAlignment = Alignment.Top) {
        Text(emoji, fontSize = 18.sp)
        Spacer(Modifier.width(10.dp))
        Column {
            Text(title, color = GreyText, fontSize = 12.sp)
            Text(address, fontSize = 14.sp, fontWeight = FontWeight.Medium)
        }
    }
}

@Composable
private fun Meta(label: String, value: String, valueColor: Color = DarkText) {
    Column {
        Text(label, color = GreyText, fontSize = 12.sp)
        Text(value, color = valueColor, fontSize = 15.sp, fontWeight = FontWeight.Bold)
    }
}