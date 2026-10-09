package org.example.project.feature.drawer.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
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
import org.example.project.components.LocalToast
import org.example.project.components.ScreenScaffold
import org.example.project.theme.*

private data class Notice(
    val id: Int, val emoji: String, val title: String,
    val message: String, val time: String, val unread: Boolean
)

@Composable
fun NotificationsScreen(onBack: () -> Unit, onUnreadChange: (Int) -> Unit = {}) {
    val toast = LocalToast.current
    var menuOpen by remember { mutableStateOf(false) }
    val items = remember {
        mutableStateListOf(
            Notice(1, "\uD83D\uDCE6", "New order assigned", "Order #1042 from Star Kabab is waiting for pickup.", "2 min ago", true),
            Notice(2, "\uD83D\uDCB0", "Weekly payout ready", "\u09F3 8,920 is ready to withdraw.", "1 hour ago", true),
            Notice(3, "\uD83C\uDF81", "Bonus unlocked", "Complete 3 more orders today to earn a \u09F3 150 bonus.", "3 hours ago", true),
            Notice(4, "\u2705", "Document approved", "Your driving license was verified.", "Yesterday", false),
            Notice(5, "\uD83D\uDCE2", "App update", "Version 1.0.1 brings faster order alerts.", "2 days ago", false)
        )
    }
    val unreadCount = items.count { it.unread }
    LaunchedEffect(unreadCount) { onUnreadChange(unreadCount) }

    ScreenScaffold(
        title = "Notifications",
        onBack = onBack,
        actions = {
            Box {
                IconButton(onClick = { menuOpen = true }) {
                    Icon(Icons.Filled.MoreVert, contentDescription = "More options")
                }
                DropdownMenu(expanded = menuOpen, onDismissRequest = { menuOpen = false }) {
                    DropdownMenuItem(
                        text = { Text("Mark all as read") },
                        enabled = items.any { it.unread },
                        onClick = {
                            menuOpen = false
                            items.indices.forEach { items[it] = items[it].copy(unread = false) }
                            toast.success("All notifications marked as read")
                        }
                    )
                    DropdownMenuItem(
                        text = { Text("Clear all") },
                        enabled = items.isNotEmpty(),
                        onClick = {
                            menuOpen = false
                            items.clear()
                            toast.info("All notifications cleared")
                        }
                    )
                }
            }
        }
    ) {
        if (items.isEmpty()) {
            ContentCard {
                Column(
                    modifier = Modifier.fillMaxWidth().padding(32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("\uD83D\uDD14", fontSize = 40.sp)
                    Spacer(Modifier.height(8.dp))
                    Text("No notifications", fontWeight = FontWeight.SemiBold)
                    Text("You're all caught up", color = GreyText, fontSize = 13.sp)
                }
            }
        } else {
            ContentCard {
                items.forEachIndexed { index, n ->
                    if (index > 0) HorizontalDivider(Modifier.padding(horizontal = 16.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { items[index] = n.copy(unread = false) }
                            .background(if (n.unread) OrangeLight.copy(alpha = 0.35f) else Color.Transparent)
                            .padding(16.dp),
                        verticalAlignment = Alignment.Top
                    ) {
                        Box(
                            Modifier.size(44.dp).clip(CircleShape).background(OrangeLight),
                            contentAlignment = Alignment.Center
                        ) { Text(n.emoji, fontSize = 20.sp) }
                        Spacer(Modifier.width(12.dp))
                        Column(Modifier.weight(1f)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(n.title, fontWeight = FontWeight.SemiBold, fontSize = 15.sp, modifier = Modifier.weight(1f))
                                if (n.unread) Box(Modifier.size(8.dp).clip(CircleShape).background(Orange))
                            }
                            Spacer(Modifier.height(2.dp))
                            Text(n.message, color = GreyText, fontSize = 13.sp)
                            Spacer(Modifier.height(4.dp))
                            Text(n.time, color = GreyText, fontSize = 11.sp)
                        }
                    }
                }
            }
        }
    }
}