package org.example.project.feature.earnings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.example.project.components.AppCard
import org.example.project.components.StatCard
import org.example.project.model.DayEarning
import org.example.project.model.SampleData
import org.example.project.theme.*

@Composable
fun EarningsScreen(onOpen: (EarningsDestination) -> Unit = {}) {
    var tab by remember { mutableStateOf(1) }
    val week = SampleData.week
    val today = week[5]

    val shown: List<DayEarning> = if (tab == 0) listOf(today) else week
    val total = shown.sumOf { it.amount }
    val orders = shown.sumOf { it.orders }

    Column(
        Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(20.dp)
    ) {
        Text("Earnings", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(12.dp))

        TabRow(selectedTabIndex = tab, containerColor = MaterialTheme.colorScheme.background) {
            listOf("Today", "This week").forEachIndexed { i, t ->
                Tab(selected = tab == i, onClick = { tab = i }, text = { Text(t) })
            }
        }

        Spacer(Modifier.height(16.dp))

        Card(
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(Modifier.padding(20.dp)) {
                Text(
                    if (tab == 0) "Today's earnings" else "This week's earnings",
                    color = Color.White.copy(alpha = 0.9f), fontSize = 14.sp
                )
                Spacer(Modifier.height(4.dp))
                Text("\u09F3 $total", color = Color.White, fontSize = 34.sp, fontWeight = FontWeight.Bold)
                Text("$orders orders completed", color = Color.White.copy(alpha = 0.9f), fontSize = 13.sp)
            }
        }

        Spacer(Modifier.height(12.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            StatCard("Avg / order", "\u09F3 ${if (orders == 0) 0 else total / orders}", "\uD83D\uDCCA", Modifier.weight(1f))
            StatCard("Tips", "\u09F3 ${total / 20}", "\uD83C\uDF81", Modifier.weight(1f))
        }

        if (tab == 1) {
            Spacer(Modifier.height(20.dp))
            Text("Daily overview", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(12.dp))
            BarChart(week)
        }

        Spacer(Modifier.height(20.dp))
        Text("Breakdown", fontSize = 18.sp, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(12.dp))

        AppCard(
            shape = RoundedCornerShape(18.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
                shown.reversed().forEachIndexed { i, d ->
                    if (i > 0) HorizontalDivider()
                    Row(
                        Modifier.fillMaxWidth().padding(vertical = 12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(Modifier.weight(1f)) {
                            Text(d.day, fontWeight = FontWeight.SemiBold)
                            Text("${d.orders} orders", color = GreyText, fontSize = 12.sp)
                        }
                        Text("\u09F3 ${d.amount}", color = Green, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }

        Spacer(Modifier.height(20.dp))
        Text("More", fontSize = 18.sp, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(12.dp))
        EarningsShortcuts(onOpen)

        Spacer(Modifier.height(20.dp))
        Button(
            onClick = { onOpen(EarningsDestination.WITHDRAW) },
            shape = RoundedCornerShape(14.dp),
            modifier = Modifier.fillMaxWidth().height(52.dp)
        ) { Text("Withdraw earnings", fontWeight = FontWeight.SemiBold) }
    }
}

@Composable
private fun BarChart(data: List<DayEarning>) {
    val max = data.maxOf { it.amount }.toFloat()
    AppCard(
        shape = RoundedCornerShape(18.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            Modifier.fillMaxWidth().padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Bottom
        ) {
            data.forEach { d ->
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("${d.amount / 100}", fontSize = 10.sp, color = GreyText)
                    Spacer(Modifier.height(4.dp))
                    Box(
                        Modifier
                            .width(26.dp)
                            .height((100f * d.amount / max).dp)
                            .clip(RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp))
                            .background(if (d.amount.toFloat() == max) Orange else OrangeLight)
                    )
                    Spacer(Modifier.height(6.dp))
                    Text(d.day, fontSize = 12.sp, color = GreyText)
                }
            }
        }
    }
}