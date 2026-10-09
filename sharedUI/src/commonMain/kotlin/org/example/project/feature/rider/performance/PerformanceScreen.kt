package org.example.project.feature.rider.performance

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.example.project.components.*
import org.example.project.model.SampleData
import org.example.project.theme.*

@Composable
fun PerformanceScreen(onBack: () -> Unit) {
    val p = SampleData.performance
    ScreenScaffold(title = "Performance", onBack = onBack) {
        ContentCard {
            Row(Modifier.fillMaxWidth().padding(16.dp)) {
                Column(Modifier.weight(1f)) {
                    Text("Completed", color = GreyText, fontSize = 13.sp)
                    Text("${p.completed}", fontSize = 26.sp, fontWeight = FontWeight.Bold)
                }
                Column(Modifier.weight(1f)) {
                    Text("Rating", color = GreyText, fontSize = 13.sp)
                    Text("${p.rating} \u2B50", fontSize = 26.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
        Spacer(Modifier.height(16.dp))

        ProgressBarCard(
            title = "Acceptance rate", subtitle = "Target 90% or above",
            progress = p.acceptanceRate / 100f, trailing = "${p.acceptanceRate}%",
            color = if (p.acceptanceRate >= 90) Green else Orange
        )
        Spacer(Modifier.height(10.dp))
        ProgressBarCard(
            title = "On-time rate", subtitle = "Target 85% or above",
            progress = p.onTimeRate / 100f, trailing = "${p.onTimeRate}%",
            color = if (p.onTimeRate >= 85) Green else Orange
        )
        Spacer(Modifier.height(10.dp))
        ProgressBarCard(
            title = "Cancellation rate", subtitle = "5% er niche rakho",
            progress = p.cancellationRate / 100f, trailing = "${p.cancellationRate}%",
            color = if (p.cancellationRate <= 5) Green else Color(0xFFD93025)
        )
    }
}