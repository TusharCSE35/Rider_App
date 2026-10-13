package org.example.project.feature.order.incoming

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.example.project.components.CountdownTimer
import org.example.project.components.StatusChip
import org.example.project.model.Order
import org.example.project.model.PaymentType
import org.example.project.theme.*

private const val OFFER_SECONDS = 30

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IncomingOrderSheet(
    order: Order,
    onAccept: () -> Unit,
    onReject: () -> Unit,
    onTimeout: () -> Unit,
    onViewDetails: (() -> Unit)? = null
) {
    ModalBottomSheet(
        onDismissRequest = {},
        sheetState = rememberModalBottomSheetState(
            skipPartiallyExpanded = true,
            confirmValueChange = { it != SheetValue.Hidden }
        ),
        containerColor = MaterialTheme.colorScheme.surface
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .navigationBarsPadding()
                .padding(horizontal = 20.dp)
                .padding(bottom = 20.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Column(Modifier.weight(1f)) {
                    Text("New order request", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                    Text("${order.id}  \u2022  ${order.distanceKm} km away", color = GreyText, fontSize = 13.sp)
                }
                CountdownTimer(totalSeconds = OFFER_SECONDS, onFinish = onTimeout)
            }

            Spacer(Modifier.height(16.dp))
            Text("\uD83C\uDF54 ${order.restaurant}", fontSize = 16.sp, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(12.dp))
            OfferAddress("\uD83C\uDFEA", "Pickup", order.pickupAddress)
            Spacer(Modifier.height(8.dp))
            OfferAddress("\uD83D\uDCCD", "Drop", order.dropAddress)

            HorizontalDivider(Modifier.padding(vertical = 14.dp))

            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                OfferMeta("You earn", "\u09F3 ${order.earning}", Green)
                OfferMeta("Items", "${order.items}")
                StatusChip(
                    text = order.paymentType.label,
                    color = if (order.paymentType.isCod) Orange else Green
                )
            }

            Spacer(Modifier.height(10.dp))
            if (order.paymentType.isCod) {
                Text(
                    "Collect \u09F3 ${order.collectAmount} from customer (Cash)",
                    color = GreyText,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold
                )
            } else {
                Text(
                    "No collection needed \u2022 Paid via ${order.paymentType.label}",
                    color = Green,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }

            if (onViewDetails != null) {
                TextButton(onClick = onViewDetails, modifier = Modifier.fillMaxWidth()) {
                    Text("View full order details")
                }
            }

            Spacer(Modifier.height(20.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                OutlinedButton(
                    onClick = onReject,
                    shape = RoundedCornerShape(14.dp),
                    modifier = Modifier.weight(1f).height(52.dp)
                ) { Text("Reject", fontWeight = FontWeight.SemiBold) }
                Button(
                    onClick = onAccept,
                    shape = RoundedCornerShape(14.dp),
                    modifier = Modifier.weight(1.4f).height(52.dp)
                ) { Text("Accept order", fontSize = 16.sp, fontWeight = FontWeight.SemiBold) }
            }
        }
    }
}

@Composable
private fun OfferAddress(emoji: String, title: String, address: String) {
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
private fun OfferMeta(label: String, value: String, valueColor: androidx.compose.ui.graphics.Color = DarkText) {
    Column {
        Text(label, color = GreyText, fontSize = 12.sp)
        Text(value, color = valueColor, fontSize = 16.sp, fontWeight = FontWeight.Bold)
    }
}