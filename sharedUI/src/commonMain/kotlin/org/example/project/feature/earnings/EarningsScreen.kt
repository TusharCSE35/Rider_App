package org.example.project.feature.earnings

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.example.project.components.AppCard
import org.example.project.components.ScreenHeader
import org.example.project.components.ScreenSpacing
import org.example.project.components.SearchFilterIcon
import org.example.project.components.SegmentIconCell
import org.example.project.components.SegmentTextCell
import org.example.project.components.SegmentedFilterBar
import org.example.project.components.StatCard
import org.example.project.model.DayEarning
import org.example.project.model.RiderSession
import org.example.project.model.SampleData
import org.example.project.theme.*

@Composable
fun EarningsScreen(onOpen: (EarningsDestination) -> Unit = {}) {
    var tab by remember { mutableStateOf(1) }
    val week = SampleData.week
    val month = SampleData.month
    // Today includes completed orders from this session:
    // delivery earnings + tips added; COD cash tracked separately in Cash on hand.
    val baseToday = week[5]
    val today = baseToday.copy(
        amount = baseToday.amount + RiderSession.sessionEarnings,
        orders = baseToday.orders + RiderSession.sessionDeliveries
    )

    // Draft selection (what the user picked in the fields, not yet applied)
    var draftStartIndex by remember { mutableStateOf<Int?>(null) }
    var draftEndIndex by remember { mutableStateOf<Int?>(null) }
    // Applied filter (only set when user taps Apply with both dates chosen)
    var appliedStartIndex by remember { mutableStateOf<Int?>(null) }
    var appliedEndIndex by remember { mutableStateOf<Int?>(null) }
    var dateDialog by remember { mutableStateOf<String?>(null) }

    val isCustom = tab == 3
    val hasAppliedFilter = appliedStartIndex != null && appliedEndIndex != null
    val showCustomResults = isCustom && hasAppliedFilter

    val customShown: List<DayEarning> = if (hasAppliedFilter) {
        val s = minOf(appliedStartIndex!!, appliedEndIndex!!)
        val e = maxOf(appliedStartIndex!!, appliedEndIndex!!)
        month.subList(s, e + 1)
    } else {
        emptyList()
    }

    val shown: List<DayEarning> = when (tab) {
        0 -> listOf(today)
        1 -> week
        2 -> month
        else -> customShown
    }
    val total = shown.sumOf { it.amount }
    val orders = shown.sumOf { it.orders }

    val summaryTitle = when (tab) {
        0 -> "Today's earnings"
        1 -> "This week's earnings"
        2 -> "This month's earnings"
        else -> {
            if (hasAppliedFilter) {
                val s = minOf(appliedStartIndex!!, appliedEndIndex!!)
                val e = maxOf(appliedStartIndex!!, appliedEndIndex!!)
                "${month[s].fullDate} - ${month[e].fullDate}"
            } else {
                "Custom range"
            }
        }
    }

    // Results are visible for presets always; for custom only after Apply.
    val showResults = !isCustom || showCustomResults

    Column(Modifier.fillMaxSize()) {
        ScreenHeader(
            title = "Earnings",
            subtitle = "Track your income"
        )
        Column(
            Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(
                    start = ScreenSpacing.sides,
                    end = ScreenSpacing.sides,
                    top = ScreenSpacing.belowHeader,
                    bottom = ScreenSpacing.sides
                )
        ) {
        FilterSegmentedBar(
            selected = tab,
            onSelect = { tab = it }
        )

        if (isCustom) {
            Spacer(Modifier.height(ScreenSpacing.belowFilter))
            AppCard(
                shape = RoundedCornerShape(18.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(Modifier.padding(16.dp)) {
                    Text("Custom range", fontWeight = FontWeight.Bold, fontSize = 15.sp)
                    Spacer(Modifier.height(12.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        Column(Modifier.weight(1f)) {
                            Text("Start date", color = GreyText, fontSize = 12.sp)
                            Spacer(Modifier.height(4.dp))
                            OutlinedButton(
                                onClick = { dateDialog = "start" },
                                shape = RoundedCornerShape(12.dp),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(
                                    draftStartIndex?.let { month[it].fullDate } ?: "Select date",
                                    maxLines = 1,
                                    color = if (draftStartIndex != null) Color.Unspecified else GreyText
                                )
                            }
                        }
                        Column(Modifier.weight(1f)) {
                            Text("End date", color = GreyText, fontSize = 12.sp)
                            Spacer(Modifier.height(4.dp))
                            OutlinedButton(
                                onClick = { dateDialog = "end" },
                                shape = RoundedCornerShape(12.dp),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(
                                    draftEndIndex?.let { month[it].fullDate } ?: "Select date",
                                    maxLines = 1,
                                    color = if (draftEndIndex != null) Color.Unspecified else GreyText
                                )
                            }
                        }
                    }
                    Spacer(Modifier.height(12.dp))
                    Button(
                        onClick = {
                            appliedStartIndex = draftStartIndex
                            appliedEndIndex = draftEndIndex
                        },
                        enabled = draftStartIndex != null && draftEndIndex != null,
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth().height(48.dp)
                    ) { Text("Apply filter", fontWeight = FontWeight.SemiBold) }
                    if (draftStartIndex != null && draftEndIndex != null && draftStartIndex!! > draftEndIndex!!) {
                        Spacer(Modifier.height(8.dp))
                        Text(
                            "Start is after end — range will be swapped on apply",
                            color = Orange, fontSize = 12.sp
                        )
                    }
                    if (hasAppliedFilter && (draftStartIndex != appliedStartIndex || draftEndIndex != appliedEndIndex)) {
                        Spacer(Modifier.height(8.dp))
                        Text(
                            "Selection changed — tap Apply to update results",
                            color = GreyText, fontSize = 12.sp
                        )
                    }
                }
            }
        }

        Spacer(Modifier.height(ScreenSpacing.belowFilter))

        if (showResults) {
            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(Modifier.padding(20.dp)) {
                    Text(
                        summaryTitle,
                        color = Color.White.copy(alpha = 0.9f), fontSize = 14.sp
                    )
                    Spacer(Modifier.height(4.dp))
                    Text("৳ $total", color = Color.White, fontSize = 34.sp, fontWeight = FontWeight.Bold)
                    Text("$orders orders completed", color = Color.White.copy(alpha = 0.9f), fontSize = 13.sp)
                }
            }

            if (RiderSession.sessionCashCollected > 0) {
                Spacer(Modifier.height(12.dp))
                AppCard(
                    shape = RoundedCornerShape(18.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(Modifier.fillMaxWidth().padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                        Column(Modifier.weight(1f)) {
                            Text("Cash collected today (COD)", color = GreyText, fontSize = 12.sp)
                            Text(
                                "\u09F3 ${RiderSession.sessionCashCollected} to deposit at branch",
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp
                            )
                        }
                        Text("\uD83D\uDCB5", fontSize = 24.sp)
                    }
                }
            }

            Spacer(Modifier.height(12.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                StatCard("Avg / order", "৳ ${if (orders == 0) 0 else total / orders}", "📊", Modifier.weight(1f))
                StatCard("Tips", "৳ ${total / 20}", "🎁", Modifier.weight(1f))
            }

            if (tab != 0) {
                Spacer(Modifier.height(20.dp))
                Text("Daily overview", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                Spacer(Modifier.height(12.dp))
                BarChart(shown)
            }

            Spacer(Modifier.height(20.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    "Breakdown (${shown.size} days)",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f)
                )
                if (shown.size > 5) {
                    Text("Scroll for more", color = GreyText, fontSize = 12.sp)
                }
            }
            Spacer(Modifier.height(12.dp))

            AppCard(
                shape = RoundedCornerShape(18.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    Modifier
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                        .heightIn(max = 340.dp)
                        .verticalScroll(rememberScrollState())
                ) {
                    shown.reversed().forEachIndexed { i, d ->
                        if (i > 0) HorizontalDivider()
                        Row(
                            Modifier.fillMaxWidth().padding(vertical = 12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(Modifier.weight(1f)) {
                                Text(d.fullDate, fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
                                Text("${d.orders} orders", color = GreyText, fontSize = 12.sp)
                            }
                            Text("৳ ${d.amount}", color = Green, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        } else {
            // Custom tab opened but filter not applied yet — prompt, no earnings shown.
            AppCard(
                shape = RoundedCornerShape(18.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("📅", fontSize = 36.sp)
                    Spacer(Modifier.height(12.dp))
                    Text("No filter applied yet", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                    Spacer(Modifier.height(4.dp))
                    Text(
                        "Tap Start Date and End Date above, then press Apply to view earnings.",
                        color = GreyText, fontSize = 13.sp, textAlign = TextAlign.Center
                    )
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

    if (dateDialog != null) {
        val isStart = dateDialog == "start"
        CalendarPickerDialog(
            title = if (isStart) "Select start date" else "Select end date",
            dates = month,
            selectedIndex = if (isStart) draftStartIndex else draftEndIndex,
            onSelect = { index ->
                if (isStart) draftStartIndex = index else draftEndIndex = index
                dateDialog = null
            },
            onDismiss = { dateDialog = null }
        )
    }
}

private fun shortDayLabel(day: String): String =
    if (" " in day) day.substringAfterLast(" ") else day

@Composable
private fun CalendarPickerDialog(
    title: String,
    dates: List<DayEarning>,
    selectedIndex: Int?,
    onSelect: (Int) -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(title) },
        text = {
            Column(
                Modifier
                    .fillMaxWidth()
                    .heightIn(max = 420.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                // Weekday header
                Row(Modifier.fillMaxWidth()) {
                    listOf("S", "M", "T", "W", "T", "F", "S").forEach { w ->
                        Text(
                            w,
                            color = GreyText, fontSize = 11.sp,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
                Spacer(Modifier.height(8.dp))
                // Calendar-style grid of available dates (no amounts shown)
                dates.chunked(7).forEach { rowDates ->
                    Row(
                        Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        val rowStart = dates.indexOf(rowDates.first())
                        rowDates.forEachIndexed { col, d ->
                            val index = rowStart + col
                            val selected = index == selectedIndex
                            Box(
                                contentAlignment = Alignment.Center,
                                modifier = Modifier
                                    .weight(1f)
                                    .aspectRatio(1f)
                                    .padding(2.dp)
                                    .clip(CircleShape)
                                    .background(
                                        if (selected) MaterialTheme.colorScheme.primary
                                        else Color.Transparent
                                    )
                                    .clickable { onSelect(index) }
                            ) {
                                Text(
                                    shortDayLabel(d.day),
                                    fontSize = 13.sp,
                                    maxLines = 1,
                                    fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal,
                                    color = if (selected) Color.White else Color.Unspecified
                                )
                            }
                        }
                        // Pad incomplete last row so cells keep calendar alignment
                        repeat(7 - rowDates.size) {
                            Spacer(Modifier.weight(1f).aspectRatio(1f))
                        }
                    }
                }
                Spacer(Modifier.height(8.dp))
                Text(
                    if (selectedIndex != null) "Selected: ${dates[selectedIndex].fullDate}"
                    else "Tap a date to select it",
                    color = GreyText, fontSize = 12.sp
                )
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) { Text("Cancel") }
        }
    )
}

@Composable
private fun FilterSegmentedBar(
    selected: Int,
    onSelect: (Int) -> Unit
) {
    val labels = listOf("Today", "This Week", "This Month")
    SegmentedFilterBar {
        labels.forEachIndexed { i, label ->
            SegmentTextCell(
                label = label,
                selected = selected == i,
                onClick = { onSelect(i) }
            )
        }
        // 4th equal section: Customise with custom search-style icon
        SegmentIconCell(
            selected = selected == 3,
            onClick = { onSelect(3) },
            icon = { tint -> SearchFilterIcon(tint = tint) }
        )
    }
}

@Composable
private fun BarChart(data: List<DayEarning>) {
    if (data.isEmpty()) return
    val max = data.maxOf { it.amount }.toFloat()
    AppCard(
        shape = RoundedCornerShape(18.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        if (data.size <= 7) {
            Row(
                Modifier.fillMaxWidth().padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom
            ) {
                data.forEach { d ->
                    BarColumn(d, max, data.size)
                }
            }
        } else {
            Row(
                Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState())
                    .padding(16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.Bottom
            ) {
                data.forEach { d ->
                    BarColumn(d, max, data.size)
                }
            }
        }
    }
}

@Composable
private fun BarColumn(d: DayEarning, max: Float, totalSize: Int) {
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
        Text(
            if (totalSize > 7) d.day.takeLast(2).trimStart() else d.day,
            fontSize = 12.sp, color = GreyText, maxLines = 1
        )
    }
}
