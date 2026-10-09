package org.example.project.feature.rider.leaderboard

import androidx.compose.foundation.layout.*
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.example.project.components.*
import org.example.project.model.SampleData
import org.example.project.theme.*

@Composable
fun LeaderboardScreen(onBack: () -> Unit) {
    ScreenScaffold(title = "Leaderboard", onBack = onBack) {
        Text("Top riders of this week", color = GreyText, fontSize = 13.sp)
        Spacer(Modifier.height(12.dp))
        ContentCard {
            Column(Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
                SampleData.leaderboard.forEachIndexed { i, e ->
                    if (i > 0) HorizontalDivider()
                    Row(Modifier.fillMaxWidth().padding(vertical = 12.dp), verticalAlignment = Alignment.CenterVertically) {
                        val medal = when (e.rank) {
                            1 -> "\uD83E\uDD47"
                            2 -> "\uD83E\uDD48"
                            3 -> "\uD83E\uDD49"
                            else -> "#${e.rank}"
                        }
                        Text(medal, fontSize = 18.sp, modifier = Modifier.width(44.dp))
                        Text(
                            if (e.isMe) "${e.name} (You)" else e.name,
                            modifier = Modifier.weight(1f),
                            fontWeight = if (e.isMe) FontWeight.Bold else FontWeight.Medium,
                            color = if (e.isMe) Orange else DarkText
                        )
                        Text("${e.orders} orders", fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
                    }
                }
            }
        }
    }
}