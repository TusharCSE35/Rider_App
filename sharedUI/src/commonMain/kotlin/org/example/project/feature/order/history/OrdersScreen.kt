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
import org.example.project.components.ScreenHeader
import org.example.project.components.ScreenSpacing
import org.example.project.components.SegmentTextCell
import org.example.project.components.SegmentedFilterBar
import org.example.project.model.Order
import org.example.project.model.OrderStatus
import org.example.project.model.SampleData
import org.example.project.theme.GreyText

private val filters = listOf("Active", "Completed", "Cancelled")

@Composable
fun OrdersScreen(
    activeOrder: Order? = null,
    onOrderClick: (Order) -> Unit = {}
) {
    var tab by remember { mutableStateOf(0) }

    val list = when (tab) {
        0 -> listOfNotNull(activeOrder)
        1 -> SampleData.orders.filter { it.status == OrderStatus.DELIVERED }
        else -> SampleData.orders.filter { it.status == OrderStatus.CANCELLED }
    }

    Column(Modifier.fillMaxSize()) {
        ScreenHeader(
            title = "My Orders",
            subtitle = "Track and manage your deliveries"
        )
        Spacer(Modifier.height(ScreenSpacing.belowHeader))

        Box(Modifier.fillMaxWidth().padding(horizontal = ScreenSpacing.sides)) {
            SegmentedFilterBar {
                filters.forEachIndexed { i, title ->
                    SegmentTextCell(
                        label = title,
                        selected = tab == i,
                        onClick = { tab = i }
                    )
                }
            }
        }
        Spacer(Modifier.height(ScreenSpacing.belowFilter))

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
                contentPadding = PaddingValues(
                    start = ScreenSpacing.sides,
                    end = ScreenSpacing.sides,
                    top = 0.dp,
                    bottom = ScreenSpacing.sides
                ),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(list, key = { it.id }) { order ->
                    OrderCard(order = order, onClick = { onOrderClick(order) })
                }
            }
        }
    }
}
