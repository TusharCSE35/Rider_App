package org.example.project.feature.order.details

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.example.project.components.ContentCard
import org.example.project.components.ScreenScaffold
import org.example.project.components.SectionLabel
import org.example.project.components.StatusChip
import org.example.project.components.statusColor
import org.example.project.core.platform.rememberDialer
import org.example.project.core.platform.rememberMapLauncher
import org.example.project.model.Order
import org.example.project.model.OrderStatus
import org.example.project.model.PaymentType
import org.example.project.theme.*

@Composable
fun OrderDetailsScreen(
    order: Order,
    onBack: () -> Unit,
    onAdvance: () -> Unit,
    onCancel: () -> Unit,
    onViewMap: () -> Unit
) {
    val call = rememberDialer()
    val navigate = rememberMapLauncher()
    val pickupStage = order.status == OrderStatus.ACCEPTED

    ScreenScaffold(title = "Order ${order.id}", onBack = onBack) {
        ContentCard {
            Row(Modifier.fillMaxWidth().padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                Column(Modifier.weight(1f)) {
                    Text(order.restaurant, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                    Text(order.time, color = GreyText, fontSize = 12.sp)
                }
                StatusChip(order.status.label, statusColor(order.status))
            }
        }
        Spacer(Modifier.height(12.dp))
        OutlinedButton(
            onClick = onViewMap,
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.fillMaxWidth().height(44.dp)
        ) { Text("View route on map") }
        Spacer(Modifier.height(20.dp))
        SectionLabel("Pickup")
        ContentCard {
            ContactBlock(
                title = order.restaurant,
                address = order.pickupAddress,
                phone = order.restaurantPhone,
                highlight = pickupStage,
                onCall = { call(order.restaurantPhone) },
                onNavigate = { navigate(order.pickupLat, order.pickupLng) }
            )
        }

        Spacer(Modifier.height(20.dp))
        SectionLabel("Drop")
        ContentCard {
            ContactBlock(
                title = order.customer,
                address = order.dropAddress,
                phone = order.customerPhone,
                highlight = !pickupStage,
                onCall = { call(order.customerPhone) },
                onNavigate = { navigate(order.dropLat, order.dropLng) }
            )
        }

        if (order.customerNote.isNotBlank()) {
            Spacer(Modifier.height(20.dp))
            SectionLabel("Customer note")
            ContentCard {
                Text(order.customerNote, modifier = Modifier.padding(16.dp), fontSize = 14.sp)
            }
        }

        Spacer(Modifier.height(20.dp))
        SectionLabel("Items (${order.items})")
        ContentCard {
            if (order.itemList.isEmpty()) {
                Text("${order.items} items", modifier = Modifier.padding(16.dp), color = GreyText)
            } else {
                Column(Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
                    order.itemList.forEachIndexed { i, item ->
                        if (i > 0) HorizontalDivider()
                        Row(
                            Modifier.fillMaxWidth().padding(vertical = 12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("${item.qty}x", color = Orange, fontWeight = FontWeight.Bold)
                            Spacer(Modifier.width(12.dp))
                            Text(item.name, modifier = Modifier.weight(1f), fontSize = 14.sp)
                            Text("\u09F3 ${item.total}", fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
                        }
                    }
                }
            }
        }

        Spacer(Modifier.height(20.dp))
        SectionLabel("Payment")
        ContentCard {
            Column(Modifier.padding(16.dp)) {
                PaymentRow("Payment type") {
                    StatusChip(
                        order.paymentType.label,
                        if (order.paymentType == PaymentType.COD) Orange else Green
                    )
                }
                HorizontalDivider(Modifier.padding(vertical = 10.dp))
                PaymentRow("Collect from customer") {
                    Text(
                        if (order.paymentType == PaymentType.COD) "\u09F3 ${order.collectAmount}" else "\u09F3 0",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                }
                HorizontalDivider(Modifier.padding(vertical = 10.dp))
                PaymentRow("Your earning") {
                    Text("\u09F3 ${order.earning}", color = Green, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                }
            }
        }

        val label = order.status.actionLabel
        if (label != null) {
            Spacer(Modifier.height(24.dp))
            Button(
                onClick = onAdvance,
                shape = RoundedCornerShape(14.dp),
                modifier = Modifier.fillMaxWidth().height(54.dp)
            ) { Text(label, fontSize = 16.sp, fontWeight = FontWeight.SemiBold) }
        }
        if (order.status.isActive) {
            TextButton(
                onClick = onCancel,
                modifier = Modifier.fillMaxWidth()
            ) { Text("Cancel / report issue", color = androidx.compose.ui.graphics.Color(0xFFD93025)) }
        }
        Spacer(Modifier.height(8.dp))
    }
}

@Composable
private fun ContactBlock(
    title: String,
    address: String,
    phone: String,
    highlight: Boolean,
    onCall: () -> Unit,
    onNavigate: () -> Unit
) {
    Column(Modifier.padding(16.dp)) {
        Text(title, fontWeight = FontWeight.SemiBold, fontSize = 15.sp)
        Spacer(Modifier.height(2.dp))
        Text(address, color = GreyText, fontSize = 13.sp)
        Spacer(Modifier.height(12.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            OutlinedButton(
                onClick = onCall,
                enabled = phone.isNotBlank(),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.weight(1f).height(44.dp)
            ) { Text("Call") }
            if (highlight) {
                Button(
                    onClick = onNavigate,
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.weight(1f).height(44.dp)
                ) { Text("Navigate") }
            } else {
                OutlinedButton(
                    onClick = onNavigate,
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.weight(1f).height(44.dp)
                ) { Text("Navigate") }
            }
        }
    }
}

@Composable
private fun PaymentRow(label: String, value: @Composable () -> Unit) {
    Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        Text(label, color = GreyText, fontSize = 14.sp, modifier = Modifier.weight(1f))
        value()
    }
}