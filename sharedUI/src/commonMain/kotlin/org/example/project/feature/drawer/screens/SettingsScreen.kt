package org.example.project.feature.drawer.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.example.project.components.ContentCard
import org.example.project.components.ScreenScaffold
import org.example.project.components.SectionLabel
import org.example.project.theme.*

private val Red = Color(0xFFD32F2F)

@Composable
fun SettingsScreen(onBack: () -> Unit) {
    var orderAlerts by remember { mutableStateOf(true) }
    var sound by remember { mutableStateOf(true) }
    var vibration by remember { mutableStateOf(true) }
    var language by remember { mutableStateOf("English") }
    var confirmDelete by remember { mutableStateOf(false) }

    ScreenScaffold(title = "Settings", onBack = onBack) {
        SectionLabel("Notifications")
        ContentCard {
            SwitchRow("\uD83D\uDD14", "Order alerts", "Get notified for new orders", orderAlerts) { orderAlerts = it }
            HorizontalDivider(Modifier.padding(horizontal = 16.dp))
            SwitchRow("\uD83D\uDD0A", "Sound", "Play a sound for new orders", sound) { sound = it }
            HorizontalDivider(Modifier.padding(horizontal = 16.dp))
            SwitchRow("\uD83D\uDCF3", "Vibration", "Vibrate for new orders", vibration) { vibration = it }
        }

        Spacer(Modifier.height(20.dp))
        SectionLabel("Language")
        ContentCard {
            Row(Modifier.padding(16.dp), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                listOf("English", "\u09AC\u09BE\u0982\u09B2\u09BE").forEach { lang ->
                    val selected = language == lang
                    Text(
                        lang,
                        color = if (selected) Color.White else DarkText,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(12.dp))
                            .background(if (selected) Orange else OrangeLight.copy(alpha = 0.5f))
                            .clickable { language = lang }
                            .padding(vertical = 12.dp),
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                    )
                }
            }
        }

        Spacer(Modifier.height(20.dp))
        SectionLabel("Preferences")
        ContentCard {
            ArrowRow("\uD83D\uDDFA\uFE0F", "Navigation app", "Google Maps") { /* TODO */ }
        }

        Spacer(Modifier.height(20.dp))
        SectionLabel("Account")
        ContentCard {
            ArrowRow("\uD83D\uDD11", "Change password", null) { /* TODO */ }
            HorizontalDivider(Modifier.padding(horizontal = 16.dp))
            ArrowRow("\uD83D\uDDD1\uFE0F", "Delete account", null, titleColor = Red) { confirmDelete = true }
        }
    }

    if (confirmDelete) {
        AlertDialog(
            onDismissRequest = { confirmDelete = false },
            title = { Text("Delete account?") },
            text = { Text("This permanently removes your rider account and earnings history.") },
            confirmButton = {
                TextButton(onClick = { confirmDelete = false /* TODO: call API */ }) {
                    Text("Delete", color = Red, fontWeight = FontWeight.SemiBold)
                }
            },
            dismissButton = { TextButton(onClick = { confirmDelete = false }) { Text("Cancel") } }
        )
    }
}

@Composable
private fun SwitchRow(emoji: String, title: String, subtitle: String, checked: Boolean, onChange: (Boolean) -> Unit) {
    Row(
        Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(emoji, fontSize = 22.sp)
        Spacer(Modifier.width(14.dp))
        Column(Modifier.weight(1f)) {
            Text(title, fontWeight = FontWeight.SemiBold, fontSize = 15.sp)
            Text(subtitle, color = GreyText, fontSize = 12.sp)
        }
        Switch(
            checked = checked, onCheckedChange = onChange,
            colors = SwitchDefaults.colors(checkedTrackColor = Orange, checkedThumbColor = Color.White)
        )
    }
}

@Composable
private fun ArrowRow(
    emoji: String, title: String, value: String?,
    titleColor: Color = DarkText, onClick: () -> Unit
) {
    Row(
        Modifier.fillMaxWidth().clickable(onClick = onClick).padding(horizontal = 16.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(emoji, fontSize = 22.sp)
        Spacer(Modifier.width(14.dp))
        Text(title, color = titleColor, fontWeight = FontWeight.SemiBold, fontSize = 15.sp, modifier = Modifier.weight(1f))
        if (value != null) Text(value, color = GreyText, fontSize = 14.sp)
        Spacer(Modifier.width(8.dp))
        Text("\u203A", color = GreyText, fontSize = 22.sp)
    }
}