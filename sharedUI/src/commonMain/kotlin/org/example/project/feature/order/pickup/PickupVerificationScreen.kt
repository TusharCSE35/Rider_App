package org.example.project.feature.order.pickup

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.example.project.components.CodeInputField
import org.example.project.components.ContentCard
import org.example.project.components.ScreenScaffold
import org.example.project.components.SectionLabel
import org.example.project.model.Order
import org.example.project.theme.*

@Composable
fun PickupVerificationScreen(
    order: Order,
    onBack: () -> Unit,
    onVerified: () -> Unit
) {
    var code by remember { mutableStateOf("") }
    var error by remember { mutableStateOf(false) }

    ScreenScaffold(title = "Pickup verification", onBack = onBack) {
        ContentCard {
            Column(Modifier.fillMaxWidth().padding(16.dp)) {
                Text(order.restaurant, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                Text("Order ${order.id} \u2022 ${order.items} items", color = GreyText, fontSize = 13.sp)
            }
        }

        Spacer(Modifier.height(20.dp))
        SectionLabel("Order code")
        Text(
            "Enter the 4-digit code from the restaurant. (Testing mode: any 4-digit code works.)",
            color = GreyText, fontSize = 13.sp
        )
        Spacer(Modifier.height(12.dp))
        CodeInputField(
            value = code,
            onValueChange = { code = it; error = false },
            isError = error,
            errorText = "Wrong Code, Try again"
        )

        Spacer(Modifier.height(24.dp))
        Button(
            onClick = {
                // TEMPORARY testing behaviour: accept any 4-digit code.
                if (code.length == 4 && code.all { it.isDigit() }) onVerified() else error = true
            },
            enabled = code.length == 4,
            shape = RoundedCornerShape(14.dp),
            modifier = Modifier.fillMaxWidth().height(54.dp)
        ) { Text("Verify & pick up", fontSize = 16.sp, fontWeight = FontWeight.SemiBold) }
    }
}