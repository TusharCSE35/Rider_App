package org.example.project.feature.order.delivery

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.example.project.components.CodeInputField
import org.example.project.components.ContentCard
import org.example.project.components.ScreenScaffold
import org.example.project.components.SectionLabel
import org.example.project.model.Order
import org.example.project.model.PaymentType
import org.example.project.theme.*

@Composable
fun DeliveryConfirmScreen(
    order: Order,
    onBack: () -> Unit,
    onConfirmed: () -> Unit
) {
    val isCod = order.paymentType == PaymentType.COD
    var otp by remember { mutableStateOf("") }
    var error by remember { mutableStateOf(false) }
    var cashCollected by remember { mutableStateOf(false) }

    ScreenScaffold(title = "Confirm delivery", onBack = onBack) {
        ContentCard {
            Column(Modifier.fillMaxWidth().padding(16.dp)) {
                Text(order.customer, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                Text(order.dropAddress, color = GreyText, fontSize = 13.sp)
            }
        }

        if (isCod) {
            Spacer(Modifier.height(20.dp))
            SectionLabel("Cash collection")
            ContentCard {
                Row(
                    Modifier
                        .fillMaxWidth()
                        .clickable { cashCollected = !cashCollected }
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(checked = cashCollected, onCheckedChange = { cashCollected = it })
                    Spacer(Modifier.width(8.dp))
                    Text(
                        "Collected \u09F3 ${order.collectAmount} from customers",
                        fontSize = 14.sp
                    )
                }
            }
        }

        Spacer(Modifier.height(20.dp))
        SectionLabel("Delivery OTP")
        Text("Enter the 4-digit OTP sent to the customer’s phone.", color = GreyText, fontSize = 13.sp)
        Spacer(Modifier.height(12.dp))
        CodeInputField(
            value = otp,
            onValueChange = { otp = it; error = false },
            isError = error,
            errorText = "Wrong OTP"
        )

        Spacer(Modifier.height(24.dp))
        Button(
            onClick = { if (otp == order.deliveryOtp) onConfirmed() else error = true },
            enabled = otp.length == 4 && (!isCod || cashCollected),
            shape = RoundedCornerShape(14.dp),
            modifier = Modifier.fillMaxWidth().height(54.dp)
        ) { Text("Confirm delivered", fontSize = 16.sp, fontWeight = FontWeight.SemiBold) }
    }
}