package org.example.project.feature.drawer.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.example.project.components.AppCard
import org.example.project.components.ContentCard
import org.example.project.components.LocalToast
import org.example.project.components.ScreenScaffold
import org.example.project.components.SectionLabel
import org.example.project.theme.*

private const val SUPPORT_PHONE = "+8801700000000"
private const val SUPPORT_EMAIL = "support@riderapp.com"

private val faqs = listOf(
    "How do I get paid?" to "Earnings are added to your balance after each delivery. You can withdraw from the Earnings tab every week.",
    "What if the customer is not answering?" to "Call the customer twice and wait 5 minutes. If there is still no answer, contact support from this screen before cancelling.",
    "Why is my document still pending?" to "Documents are reviewed within 24-48 hours. You will get a notification once approved or if we need a clearer photo.",
    "How do I change my vehicle type?" to "Go to Profile > Vehicle and contact support to update your vehicle details and registration.",
    "I can't go online. What should I do?" to "Check that all required documents are approved and your internet and location are on. If it still fails, contact support."
)

@Composable
fun HelpSupportScreen(onBack: () -> Unit) {
    var openIndex by remember { mutableStateOf(-1) }
    val uriHandler = LocalUriHandler.current
    val toast = LocalToast.current

    fun open(uri: String, failMessage: String) {
        try {
            uriHandler.openUri(uri)
        } catch (e: Exception) {
            toast.error(failMessage)
        }
    }

    ScreenScaffold(title = "Help & Support", onBack = onBack) {
        Card(
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary),
            elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(Modifier.padding(20.dp)) {
                Text("How can we help?", color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                Spacer(Modifier.height(4.dp))
                Text(
                    "Our rider support team is available 8 AM - 11 PM, every day.",
                    color = Color.White.copy(alpha = 0.9f), fontSize = 13.sp
                )
            }
        }

        Spacer(Modifier.height(20.dp))
        SectionLabel("Contact us")
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            ContactTile("\uD83D\uDCDE", "Call", Modifier.weight(1f)) {
                open("tel:$SUPPORT_PHONE", "Unable to open dialer")
            }
            ContactTile("\uD83D\uDCAC", "Chat", Modifier.weight(1f)) {
                toast.info("Live chat is coming soon")
            }
            ContactTile("\u2709\uFE0F", "Email", Modifier.weight(1f)) {
                open("mailto:$SUPPORT_EMAIL?subject=Rider%20Support", "Unable to open mail app")
            }
        }

        Spacer(Modifier.height(20.dp))
        SectionLabel("Frequently asked questions")
        ContentCard {
            faqs.forEachIndexed { i, (q, a) ->
                if (i > 0) HorizontalDivider(Modifier.padding(horizontal = 16.dp))
                val open = openIndex == i
                Column(
                    Modifier.fillMaxWidth()
                        .clickable { openIndex = if (open) -1 else i }
                        .padding(16.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(q, fontWeight = FontWeight.SemiBold, fontSize = 15.sp, modifier = Modifier.weight(1f))
                        Text(if (open) "\u2212" else "+", color = Orange, fontSize = 22.sp, fontWeight = FontWeight.Bold)
                    }
                    if (open) {
                        Spacer(Modifier.height(8.dp))
                        Text(a, color = GreyText, fontSize = 13.sp, lineHeight = 19.sp)
                    }
                }
            }
        }

        Spacer(Modifier.height(24.dp))
        OutlinedButton(
            onClick = { toast.info("Report form is coming soon") },
            shape = RoundedCornerShape(14.dp),
            modifier = Modifier.fillMaxWidth().height(52.dp)
        ) { Text("Report a problem", fontWeight = FontWeight.SemiBold) }
    }
}

@Composable
private fun ContactTile(emoji: String, label: String, modifier: Modifier, onClick: () -> Unit) {
    val shape = RoundedCornerShape(18.dp)
    AppCard(
        shape = shape,
        modifier = modifier.clip(shape).clickable(onClick = onClick)
    ) {
        Column(
            Modifier.fillMaxWidth().padding(vertical = 18.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(Modifier.size(48.dp).clip(CircleShape).background(OrangeLight), contentAlignment = Alignment.Center) {
                Text(emoji, fontSize = 22.sp)
            }
            Spacer(Modifier.height(8.dp))
            Text(label, fontWeight = FontWeight.SemiBold, fontSize = 14.sp, textAlign = TextAlign.Center)
        }
    }
}