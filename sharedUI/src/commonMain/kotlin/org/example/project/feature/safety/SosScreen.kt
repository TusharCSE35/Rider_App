package org.example.project.feature.safety

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.example.project.components.ContentCard
import org.example.project.components.LocalToast
import org.example.project.components.ScreenScaffold
import org.example.project.components.SectionLabel
import org.example.project.core.platform.rememberDialer
import org.example.project.theme.*

private val Red = Color(0xFFD93025)
private const val EMERGENCY_NUMBER = "999"
private const val SUPPORT_NUMBER = "+8809600000000" // TODO: company support number

@Composable
fun SosScreen(onBack: () -> Unit) {
    val toast = LocalToast.current
    val call = rememberDialer()
    var confirmAlert by remember { mutableStateOf(false) }

    ScreenScaffold(title = "Emergency", onBack = onBack) {
        ContentCard {
            Column(Modifier.fillMaxWidth().padding(16.dp)) {
                Text("Tap here if you’re in trouble.", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                Spacer(Modifier.height(4.dp))
                Text(
                    "An alert with your location will be sent to the support team.",
                    color = GreyText, fontSize = 13.sp
                )
            }
        }

        Spacer(Modifier.height(24.dp))
        Button(
            onClick = { confirmAlert = true },
            colors = ButtonDefaults.buttonColors(containerColor = Red),
            shape = RoundedCornerShape(20.dp),
            modifier = Modifier.fillMaxWidth().height(80.dp)
        ) { Text("\uD83C\uDD98  SEND SOS ALERT", fontSize = 18.sp, fontWeight = FontWeight.Bold) }

        Spacer(Modifier.height(24.dp))
        SectionLabel("Call for help")
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            OutlinedButton(
                onClick = { call(EMERGENCY_NUMBER) },
                shape = RoundedCornerShape(14.dp),
                modifier = Modifier.weight(1f).height(52.dp)
            ) { Text("Call 999") }
            OutlinedButton(
                onClick = { call(SUPPORT_NUMBER) },
                shape = RoundedCornerShape(14.dp),
                modifier = Modifier.weight(1f).height(52.dp)
            ) { Text("Call support") }
        }
    }

    if (confirmAlert) {
        AlertDialog(
            onDismissRequest = { confirmAlert = false },
            title = { Text("Send SOS alert?") },
            text = { Text("An emergency alert will be sent to the support team.") },
            confirmButton = {
                TextButton(onClick = {
                    confirmAlert = false
                    // TODO: Batch 6-e real API + live location
                    toast.success("SOS alert sent to support")
                }) { Text("Send", color = Red, fontWeight = FontWeight.Bold) }
            },
            dismissButton = {
                TextButton(onClick = { confirmAlert = false }) { Text("Cancel") }
            }
        )
    }
}