package org.example.project.feature.rider.leaderboard

import androidx.compose.foundation.layout.*
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
fun BadgesScreen(onBack: () -> Unit) {
    val badges = SampleData.badges
    ScreenScaffold(title = "Badges", onBack = onBack) {
        Text("${badges.count { it.unlocked }}/${badges.size} unlocked", fontWeight = FontWeight.SemiBold)
        Spacer(Modifier.height(12.dp))
        badges.forEach { b ->
            ContentCard {
                Row(Modifier.fillMaxWidth().padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                    Text(if (b.unlocked) b.emoji else "\uD83D\uDD12", fontSize = 28.sp)
                    Spacer(Modifier.width(14.dp))
                    Column(Modifier.weight(1f)) {
                        Text(b.title, fontWeight = FontWeight.Bold, fontSize = 15.sp)
                        Text(b.description, color = GreyText, fontSize = 13.sp)
                    }
                    StatusChip(if (b.unlocked) "Unlocked" else "Locked", if (b.unlocked) Green else GreyText)
                }
            }
            Spacer(Modifier.height(10.dp))
        }
    }
}