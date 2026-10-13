package org.example.project.feature.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.example.project.components.AppCard
import org.example.project.feature.rider.RiderDestination
import org.example.project.feature.rider.RiderShortcuts
import org.example.project.theme.*

@Composable
fun ProfileScreen(onOpen: (RiderDestination) -> Unit = {}) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(rememberScrollState())
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp))
                .background(MaterialTheme.colorScheme.primary)
                .statusBarsPadding()
                .padding(vertical = 28.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Box(
                    modifier = Modifier.size(88.dp).clip(CircleShape).background(Color.White),
                    contentAlignment = Alignment.Center
                ) { Text("\uD83E\uDDD1", fontSize = 46.sp) }
                Spacer(Modifier.height(12.dp))
                Text("Tushar Sarkar", color = Color.White, fontSize = 22.sp, fontWeight = FontWeight.Bold)
                Text("+880 1700 000000", color = Color.White.copy(alpha = 0.9f), fontSize = 14.sp)
                Spacer(Modifier.height(6.dp))
                Text("\u2B50 4.8  \u2022  Verified", color = Color.White, fontSize = 13.sp)
                Spacer(Modifier.height(12.dp))
                OutlinedButton(
                    onClick = { onOpen(RiderDestination.EDIT_PROFILE) },
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.White)
                ) { Text("Edit profile") }
            }
        }

        Column(Modifier.padding(20.dp)) {
            SectionCard("Personal information") {
                InfoRow("Full name", "Tushar Sarkar")
                InfoRow("Phone", "+880 1700 000000")
                InfoRow("Email", "rider@example.com")
                InfoRow("City", "Dhaka")
            }

            Spacer(Modifier.height(16.dp))
            SectionCard("Vehicle") {
                InfoRow("Type", "Motorcycle")
                InfoRow("Plate number", "DHAKA METRO-LA 12-3456")
            }

            Spacer(Modifier.height(16.dp))
            SectionCard("Documents") {
                InfoRow("National ID", "Approved", valueColor = Green)
                InfoRow("Driving license", "Approved", valueColor = Green)
                InfoRow("Vehicle registration", "Pending", valueColor = Orange)
                Spacer(Modifier.height(8.dp))
                OutlinedButton(
                    onClick = { onOpen(RiderDestination.DOCUMENTS) },
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth().height(44.dp)
                ) { Text("Manage documents") }
            }

            Spacer(Modifier.height(16.dp))
            SectionCard("Settings") {
                InfoRow("Language", "English")
                InfoRow("Notifications", "On")
            }

            Spacer(Modifier.height(20.dp))
            Text("Rider tools", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(12.dp))
            RiderShortcuts(onOpen)

            Spacer(Modifier.height(8.dp))
        }
    }
}

@Composable
private fun SectionCard(title: String, content: @Composable ColumnScope.() -> Unit) {
    AppCard(shape = RoundedCornerShape(18.dp), modifier = Modifier.fillMaxWidth()) {
        Column(Modifier.padding(16.dp)) {
            Text(title, fontSize = 16.sp, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(8.dp))
            content()
        }
    }
}

@Composable
private fun InfoRow(label: String, value: String, valueColor: Color = DarkText) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(label, color = GreyText, fontSize = 14.sp)
        Text(value, color = valueColor, fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
    }
}