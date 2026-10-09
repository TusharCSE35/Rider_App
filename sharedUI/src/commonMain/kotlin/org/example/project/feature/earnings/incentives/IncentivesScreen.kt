package org.example.project.feature.earnings.incentives

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.example.project.components.*
import org.example.project.model.SampleData
import org.example.project.theme.*

@Composable
fun IncentivesScreen(onBack: () -> Unit) {
    ScreenScaffold(title = "Incentives & bonus", onBack = onBack) {
        SampleData.incentives.forEach { inc ->
            ProgressBarCard(
                title = inc.title,
                subtitle = if (inc.isDone) "Complete! You will receive a \u09F3 ${inc.reward} bonus"
                else "Complete ${inc.remaining} more orders to earn a \u09F3 ${inc.reward} bonus",
                progress = inc.progress,
                trailing = "${inc.current}/${inc.target}",
                color = if (inc.isDone) Green else Orange
            )
            Spacer(Modifier.height(12.dp))
        }
        Text("Once you complete a bonus, it will be added to your wallet.", color = GreyText, fontSize = 13.sp)
    }
}