package org.example.project.feature.drawer.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.example.project.components.ContentCard
import org.example.project.components.ScreenScaffold
import org.example.project.theme.*

data class LegalSection(val heading: String, val body: String)

@Composable
fun LegalDocumentScreen(
    title: String,
    emoji: String,
    lastUpdated: String,
    intro: String,
    sections: List<LegalSection>,
    onBack: () -> Unit
) {
    ScreenScaffold(title = title, onBack = onBack) {
        ContentCard {
            Column(Modifier.padding(20.dp)) {
                Text(emoji, fontSize = 36.sp)
                Spacer(Modifier.height(8.dp))
                Text(title, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                Text("Last updated: $lastUpdated", color = GreyText, fontSize = 12.sp)
                Spacer(Modifier.height(12.dp))
                Text(intro, fontSize = 14.sp, lineHeight = 21.sp)
            }
        }
        Spacer(Modifier.height(16.dp))
        ContentCard {
            Column(Modifier.padding(20.dp)) {
                sections.forEachIndexed { i, s ->
                    if (i > 0) Spacer(Modifier.height(20.dp))
                    Text("${i + 1}. ${s.heading}", fontWeight = FontWeight.Bold, fontSize = 15.sp, color = Orange)
                    Spacer(Modifier.height(6.dp))
                    Text(s.body, fontSize = 14.sp, lineHeight = 21.sp)
                }
            }
        }
        Spacer(Modifier.height(16.dp))
    }
}