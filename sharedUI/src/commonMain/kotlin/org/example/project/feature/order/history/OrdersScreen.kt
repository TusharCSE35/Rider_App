package org.example.project.feature.order.history

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.example.project.components.OrderCard
import org.example.project.model.Order
import org.example.project.model.OrderStatus
import org.example.project.model.SampleData
import org.example.project.theme.GreyText

private val filters = listOf("Active", "Completed", "Cancelled")

@Composable
fun OrdersScreen(activeOrder: Order? = null) {
    var tab by remember { mutableStateOf(0) }

    val list = when (tab) {
        0 -> listOfNotNull(activeOrder)
        1 -> SampleData.orders.filter { it.status == OrderStatus.DELIVERED }
        else -> SampleData.orders.filter { it.status == OrderStatus.CANCELLED }
    }

    Column(Modifier.fillMaxSize()) {
        Text(
            "My Orders",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(start = 20.dp, top = 20.dp, bottom = 12.dp)
        )

        TabRow(
            selectedTabIndex = tab,
            containerColor = MaterialTheme.colorScheme.background
        ) {
            filters.forEachIndexed { i, title ->
                Tab(
                    selected = tab == i,
                    onClick = { tab = i },
                    text = { Text(title, fontWeight = if (tab == i) FontWeight.Bold else FontWeight.Normal) }
                )
            }
        }

        if (list.isEmpty()) {
            Column(
                Modifier.fillMaxSize().padding(24.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("\uD83D\uDCED", fontSize = 48.sp)
                Spacer(Modifier.height(8.dp))
                Text("No ${filters[tab].lowercase()} orders", fontWeight = FontWeight.SemiBold)
                Text(
                    if (tab == 0) "Go online from Home to get orders" else "Nothing here yet",
                    color = GreyText, fontSize = 13.sp
                )
            }
        } else {
            LazyColumn(
                contentPadding = PaddingValues(20.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(list, key = { it.id }) { OrderCard(it) }
            }
        }
    }
}