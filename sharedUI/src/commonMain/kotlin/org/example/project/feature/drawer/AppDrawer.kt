package org.example.project.feature.drawer


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.example.project.theme.GreyText
import org.example.project.components.LocalToast

private val Red = Color(0xFFD32F2F)

@Composable
fun AppDrawer(
    onSelectTab: (Int) -> Unit,
    onOpen: (DrawerDestination) -> Unit,
    onLogout: () -> Unit,
    unreadNotifications: Int = 3
) {
    var confirmLogout by remember { mutableStateOf(false) }
    val toast = LocalToast.current

    ModalDrawerSheet(modifier = Modifier.fillMaxWidth(0.75f)) {
        Column(Modifier.fillMaxHeight()) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.primary)
                    .padding(horizontal = 20.dp, vertical = 24.dp)
            ) {
                Box(
                    modifier = Modifier.size(64.dp).clip(CircleShape).background(Color.White),
                    contentAlignment = Alignment.Center
                ) { Text("\uD83E\uDDD1", fontSize = 34.sp) }
                Spacer(Modifier.height(12.dp))
                Text(
                    "Tushar Sarkar", color = Color.White, fontSize = 18.sp,
                    fontWeight = FontWeight.Bold, maxLines = 1, overflow = TextOverflow.Ellipsis
                )
                Text("+880 1700 000000", color = Color.White.copy(alpha = 0.9f), fontSize = 13.sp)
                Spacer(Modifier.height(4.dp))
                Text("\u2B50 4.8  \u2022  Motorcycle", color = Color.White, fontSize = 13.sp)
            }

            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
                    .padding(vertical = 8.dp)
            ) {
                DrawerItem("My Profile", "\uD83D\uDC64") { onSelectTab(3) }
                DrawerItem("My Orders", "\uD83D\uDCE6") { onSelectTab(1) }
                DrawerItem("Earnings", "\uD83D\uDCB0") { onSelectTab(2) }
                DrawerItem(
                    DrawerDestination.NOTIFICATIONS.title, DrawerDestination.NOTIFICATIONS.emoji,
                    badge = unreadNotifications.takeIf { it > 0 }?.toString()
                ) { onOpen(DrawerDestination.NOTIFICATIONS) }
                DrawerItem(DrawerDestination.SETTINGS.title, DrawerDestination.SETTINGS.emoji) {
                    onOpen(DrawerDestination.SETTINGS)
                }

                HorizontalDivider(Modifier.padding(horizontal = 20.dp, vertical = 8.dp))
                Text(
                    "SUPPORT", color = GreyText, fontSize = 12.sp, fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(start = 28.dp, top = 4.dp, bottom = 4.dp)
                )
                listOf(
                    DrawerDestination.HELP, DrawerDestination.PRIVACY,
                    DrawerDestination.TERMS, DrawerDestination.ABOUT
                ).forEach { d -> DrawerItem(d.title, d.emoji) { onOpen(d) } }
            }

            HorizontalDivider()
            NavigationDrawerItem(
                label = { Text("Logout", color = Red, fontWeight = FontWeight.SemiBold) },
                icon = { Text("\uD83D\uDEAA") },
                selected = false,
                onClick = { confirmLogout = true },
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
            )
            Text(
                "Version 1.0.0", color = GreyText, fontSize = 11.sp,
                modifier = Modifier.padding(start = 28.dp, bottom = 16.dp)
            )
        }
    }

    if (confirmLogout) {
        AlertDialog(
            onDismissRequest = { confirmLogout = false },
            title = { Text("Logout?") },
            text = { Text("You will need to sign in again to receive orders.") },
            confirmButton = {
                TextButton(onClick = {
                    confirmLogout = false
                    toast.success("Logged out successfully")
                    onLogout()
                }) {
                    Text("Logout", color = Red, fontWeight = FontWeight.SemiBold)
                }
            },
            dismissButton = { TextButton(onClick = { confirmLogout = false }) { Text("Cancel") } }
        )
    }
}

@Composable
private fun DrawerItem(label: String, emoji: String, badge: String? = null, onClick: () -> Unit) {
    NavigationDrawerItem(
        label = { Text(label, fontSize = 15.sp) },
        icon = { Text(emoji, fontSize = 18.sp) },
        badge = badge?.let { b ->
            {
                Text(
                    b, color = Color.White, fontSize = 11.sp, fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.primary)
                        .padding(horizontal = 7.dp, vertical = 2.dp)
                )
            }
        },
        selected = false,
        onClick = onClick,
        modifier = Modifier.padding(horizontal = 12.dp)
    )
}