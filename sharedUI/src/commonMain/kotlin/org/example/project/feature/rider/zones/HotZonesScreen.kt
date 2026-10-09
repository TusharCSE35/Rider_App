package org.example.project.feature.rider.zones

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.example.project.components.*
import org.example.project.model.SampleData
import org.example.project.theme.*

@Composable
fun HotZonesScreen(onBack: () -> Unit) {
    ScreenScaffold(title = "Hot zones", onBack = onBack) {
        Text("Orders are busier in these areas right now. Head there to receive orders faster.", color = GreyText, fontSize = 13.sp)
        Spacer(Modifier.height(12.dp))

        SampleData.hotZones.sortedByDescending { it.demand }.forEach { z ->
            val color = when {
                z.demand >= 0.8f -> Color(0xFFD93025)
                z.demand >= 0.5f -> Orange
                else -> Green
            }
            val label = when {
                z.demand >= 0.8f -> "Very high"
                z.demand >= 0.5f -> "High"
                else -> "Normal"
            }
            ProgressBarCard(
                title = z.name,
                subtitle = "${z.activeOrders} active orders",
                progress = z.demand,
                trailing = label,
                color = color
            )
            Spacer(Modifier.height(10.dp))
        }
    }
}