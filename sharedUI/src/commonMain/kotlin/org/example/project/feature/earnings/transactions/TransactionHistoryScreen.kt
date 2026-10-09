package org.example.project.feature.earnings.transactions

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.horizontalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.example.project.components.*
import org.example.project.model.SampleData
import org.example.project.model.TransactionType
import org.example.project.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransactionHistoryScreen(onBack: () -> Unit) {
    var filter by remember { mutableStateOf<TransactionType?>(null) }
    val list = SampleData.transactions.filter { filter == null || it.type == filter }

    ScreenScaffold(title = "Transactions", onBack = onBack) {
        Row(Modifier.horizontalScroll(rememberScrollState()), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            FilterChip(selected = filter == null, onClick = { filter = null }, label = { Text("All") })
            TransactionType.entries.forEach { t ->
                FilterChip(selected = filter == t, onClick = { filter = t }, label = { Text(t.label) })
            }
        }
        Spacer(Modifier.height(16.dp))
        ContentCard {
            Column(Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
                if (list.isEmpty()) Text("Nothing", color = GreyText, modifier = Modifier.padding(vertical = 16.dp))
                list.forEachIndexed { i, t ->
                    if (i > 0) HorizontalDivider()
                    Row(Modifier.fillMaxWidth().padding(vertical = 12.dp), verticalAlignment = Alignment.CenterVertically) {
                        Column(Modifier.weight(1f)) {
                            Text(t.title, fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
                            Text("${t.type.label} \u2022 ${t.date}", color = GreyText, fontSize = 12.sp)
                        }
                        val positive = t.amount >= 0
                        Text(
                            (if (positive) "+" else "-") + "\u09F3 ${kotlin.math.abs(t.amount)}",
                            color = if (positive) Green else Color(0xFFD93025),
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}