package org.example.project.feature.drawer.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.example.project.components.ContentCard
import org.example.project.components.ScreenScaffold
import org.example.project.theme.*

@Composable
fun AboutScreen(onBack: () -> Unit) {
    ScreenScaffold(title = "About Rider App", onBack = onBack) {
        Column(Modifier.fillMaxWidth().padding(vertical = 12.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Box(
                Modifier.size(96.dp).clip(RoundedCornerShape(28.dp)).background(Orange),
                contentAlignment = Alignment.Center
            ) { Text("\uD83D\uDEF5", fontSize = 52.sp) }
            Spacer(Modifier.height(16.dp))
            Text("Food Rider", fontSize = 24.sp, fontWeight = FontWeight.Bold)
            Text("Deliver more. Earn more.", color = GreyText, fontSize = 14.sp)
            Spacer(Modifier.height(8.dp))
            Text(
                "Version 1.0.0",
                color = Orange, fontSize = 12.sp, fontWeight = FontWeight.SemiBold,
                modifier = Modifier.clip(RoundedCornerShape(50)).background(OrangeLight).padding(horizontal = 12.dp, vertical = 4.dp)
            )
        }

        Spacer(Modifier.height(16.dp))
        ContentCard {
            Text(
                "Food Rider helps delivery partners find nearby orders, navigate to restaurants and customers, and track their earnings in one simple app.",
                fontSize = 14.sp, lineHeight = 21.sp, modifier = Modifier.padding(20.dp)
            )
        }

        Spacer(Modifier.height(16.dp))
        ContentCard {
            InfoLine("Version", "1.0.0")
            HorizontalDivider(Modifier.padding(horizontal = 16.dp))
            InfoLine("Build", "1")
            HorizontalDivider(Modifier.padding(horizontal = 16.dp))
            InfoLine("Platform", "Android & iOS")
        }

        Spacer(Modifier.height(16.dp))
        ContentCard {
            LinkLine("\u2B50", "Rate the app") { /* TODO: open store page */ }
            HorizontalDivider(Modifier.padding(horizontal = 16.dp))
            LinkLine("\uD83D\uDCE4", "Share with friends") { /* TODO: share sheet */ }
            HorizontalDivider(Modifier.padding(horizontal = 16.dp))
            LinkLine("\uD83D\uDCDC", "Open-source licenses") { /* TODO */ }
        }

        Spacer(Modifier.height(24.dp))
        Text(
            "Made with \u2764\uFE0F in Bangladesh\n\u00A9 2026 Food Rider. All rights reserved.",
            color = GreyText, fontSize = 12.sp, lineHeight = 18.sp,
            textAlign = androidx.compose.ui.text.style.TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
private fun InfoLine(label: String, value: String) {
    Row(Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 14.dp), horizontalArrangement = Arrangement.SpaceBetween) {
        Text(label, color = GreyText, fontSize = 14.sp)
        Text(value, fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
    }
}

@Composable
private fun LinkLine(emoji: String, label: String, onClick: () -> Unit) {
    Row(
        Modifier.fillMaxWidth().clickable(onClick = onClick).padding(horizontal = 16.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(emoji, fontSize = 20.sp)
        Spacer(Modifier.width(14.dp))
        Text(label, fontWeight = FontWeight.SemiBold, fontSize = 15.sp, modifier = Modifier.weight(1f))
        Text("\u203A", color = GreyText, fontSize = 22.sp)
    }
}