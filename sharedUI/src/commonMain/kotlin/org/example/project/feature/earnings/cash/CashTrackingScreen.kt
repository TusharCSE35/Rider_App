package org.example.project.feature.earnings.cash

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.example.project.components.*
import org.example.project.model.RiderSession
import org.example.project.model.SampleData
import org.example.project.theme.*

@Composable
fun CashTrackingScreen(onBack: () -> Unit) {
    val toast = LocalToast.current
    val summary = SampleData.cashSummary
    val sessionCash = RiderSession.sessionCashCollected
    val sessionEntries = RiderSession.cashEntries()
    val baseCash = summary.cashInHand
    val baseEntries = summary.entries
    var deposited by remember { mutableStateOf(false) }
    val cashInHand = if (deposited) 0 else baseCash + sessionCash
    val entries = if (deposited) emptyList() else baseEntries + sessionEntries
    val totalCollectedToday = baseCash + sessionCash
    var confirm by remember { mutableStateOf(false) }

    val ratio = cashInHand / summary.limit.toFloat()
    val barColor = if (ratio >= 0.8f) Color(0xFFD93025) else Orange

    ScreenScaffold(title = "Cash on hand", onBack = onBack) {
        ProgressBarCard(
            title = "\u09F3 $cashInHand",
            subtitle = "Limit \u09F3 ${summary.limit}",
            progress = ratio.coerceIn(0f, 1f),
            trailing = "${(ratio * 100).toInt().coerceAtMost(100)}%",
            color = barColor
        )
        if (ratio >= 0.8f) {
            Spacer(Modifier.height(8.dp))
            Text("You're approaching the limit. Please deposit the cash as soon as possible.", color = Color(0xFFD93025), fontSize = 13.sp)
        }

        Spacer(Modifier.height(12.dp))
        ContentCard {
            Row(
                Modifier.fillMaxWidth().padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(Modifier.weight(1f)) {
                    Text("Total cash collected today", color = GreyText, fontSize = 12.sp)
                    Text(
                        "\u09F3 $totalCollectedToday",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                }
                Column(horizontalAlignment = Alignment.End) {
                    Text("Deposit due at branch", color = GreyText, fontSize = 12.sp)
                    Text(
                        "\u09F3 $cashInHand",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        color = Orange
                    )
                }
            }
        }
        if (sessionCash > 0 && !deposited) {
            Spacer(Modifier.height(8.dp))
            Text(
                "Includes \u09F3 $sessionCash collected from ${sessionEntries.size} COD order(s) completed today.",
                color = GreyText,
                fontSize = 12.sp
            )
        }

        Spacer(Modifier.height(20.dp))
        SectionLabel("COD collections")
        ContentCard {
            Column(Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
                if (entries.isEmpty()) {
                    Text("There is no pending cash.", color = GreyText, modifier = Modifier.padding(vertical = 16.dp))
                }
                entries.forEachIndexed { i, e ->
                    if (i > 0) HorizontalDivider()
                    Row(Modifier.fillMaxWidth().padding(vertical = 12.dp), verticalAlignment = Alignment.CenterVertically) {
                        Column(Modifier.weight(1f)) {
                            Text("Order ${e.orderId}", fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
                            Text(e.time, color = GreyText, fontSize = 12.sp)
                        }
                        Text("\u09F3 ${e.amount}", fontWeight = FontWeight.Bold)
                    }
                }
            }
        }

        Spacer(Modifier.height(24.dp))
        Button(
            onClick = { confirm = true },
            enabled = cashInHand > 0,
            shape = RoundedCornerShape(14.dp),
            modifier = Modifier.fillMaxWidth().height(54.dp)
        ) { Text("Deposit \u09F3 $cashInHand to company", fontSize = 16.sp, fontWeight = FontWeight.SemiBold) }
    }

    if (confirm) {
        AlertDialog(
            onDismissRequest = { confirm = false },
            title = { Text("Will you deposit the cash?") },
            text = { Text("\u09F3 $cashInHand will be deposited with the company") },
            confirmButton = {
                TextButton(onClick = {
                    confirm = false
                    toast.success("\u09F3 $cashInHand deposit request has been sent")
                    deposited = true
                }) { Text("Confirm") }
            },
            dismissButton = { TextButton(onClick = { confirm = false }) { Text("Cancel") } }
        )
    }
}