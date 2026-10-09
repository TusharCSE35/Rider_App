package org.example.project.feature.earnings.tips

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.example.project.components.*
import org.example.project.model.SampleData
import org.example.project.model.TransactionType
import org.example.project.theme.*

@Composable
fun TipsBreakdownScreen(onBack: () -> Unit) {
    val tips = SampleData.transactions.filter { it.type == TransactionType.TIP }
    ScreenScaffold(title = "Tips", onBack = onBack) {
        ContentCard {
            Column(Modifier.fillMaxWidth().padding(16.dp)) {
                Text("Total tips", color = GreyText, fontSize = 13.sp)
                Text("\u09F3 ${tips.sumOf { it.amount }}", fontSize = 28.sp, fontWeight = FontWeight.Bold, color = Green)
                Text("${tips.size} ta tip", color = GreyText, fontSize = 13.sp)
            }
        }
        Spacer(Modifier.height(20.dp))
        SectionLabel("Tip history")
        ContentCard {
            Column(Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
                tips.forEachIndexed { i, t ->
                    if (i > 0) HorizontalDivider()
                    Row(Modifier.fillMaxWidth().padding(vertical = 12.dp), verticalAlignment = Alignment.CenterVertically) {
                        Column(Modifier.weight(1f)) {
                            Text(t.title, fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
                            Text(t.date, color = GreyText, fontSize = 12.sp)
                        }
                        Text("+\u09F3 ${t.amount}", color = Green, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}