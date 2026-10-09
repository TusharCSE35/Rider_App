package org.example.project.feature.order.map

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.example.project.components.ContentCard
import org.example.project.components.ScreenScaffold
import org.example.project.core.platform.rememberMapLauncher
import org.example.project.model.Order
import org.example.project.theme.*
import kotlin.math.max
import kotlin.math.min

@Composable
fun OrderMapScreen(order: Order, onBack: () -> Unit) {
    val navigate = rememberMapLauncher()

    ScreenScaffold(title = "Route \u2022 ${order.id}", onBack = onBack) {
        RouteCanvas(
            order = order,
            modifier = Modifier
                .fillMaxWidth()
                .height(280.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(MaterialTheme.colorScheme.surfaceVariant)
        )

        Spacer(Modifier.height(16.dp))
        ContentCard {
            Column(Modifier.fillMaxWidth().padding(16.dp)) {
                LegendRow(Orange, "Pickup", order.restaurant, order.pickupAddress)
                Spacer(Modifier.height(12.dp))
                LegendRow(Green, "Drop", order.customer, order.dropAddress)
                Spacer(Modifier.height(12.dp))
                Text("${order.distanceKm} km \u2022 \u09F3 ${order.earning} earning", color = GreyText, fontSize = 13.sp)
            }
        }

        Spacer(Modifier.height(20.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            OutlinedButton(
                onClick = { navigate(order.pickupLat, order.pickupLng) },
                shape = RoundedCornerShape(14.dp),
                modifier = Modifier.weight(1f).height(52.dp)
            ) { Text("To pickup") }
            Button(
                onClick = { navigate(order.dropLat, order.dropLng) },
                shape = RoundedCornerShape(14.dp),
                modifier = Modifier.weight(1f).height(52.dp)
            ) { Text("To drop") }
        }
    }
}

@Composable
private fun LegendRow(color: Color, tag: String, title: String, address: String) {
    Row(verticalAlignment = Alignment.Top) {
        Box(Modifier.padding(top = 4.dp).size(12.dp).clip(RoundedCornerShape(50)).background(color))
        Spacer(Modifier.width(10.dp))
        Column {
            Text("$tag \u2022 $title", fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
            Text(address, color = GreyText, fontSize = 13.sp)
        }
    }
}

@Composable
private fun RouteCanvas(order: Order, modifier: Modifier = Modifier) {
    val gridColor = GreyText.copy(alpha = 0.12f)
    val lineColor = GreyText

    Canvas(modifier) {
        val step = 40.dp.toPx()
        var x = step
        while (x < size.width) { drawLine(gridColor, Offset(x, 0f), Offset(x, size.height)); x += step }
        var y = step
        while (y < size.height) { drawLine(gridColor, Offset(0f, y), Offset(size.width, y)); y += step }

        val pad = 56.dp.toPx()
        val minLat = min(order.pickupLat, order.dropLat)
        val maxLat = max(order.pickupLat, order.dropLat)
        val minLng = min(order.pickupLng, order.dropLng)
        val maxLng = max(order.pickupLng, order.dropLng)
        val latSpan = max(maxLat - minLat, 0.002)
        val lngSpan = max(maxLng - minLng, 0.002)

        fun point(lat: Double, lng: Double) = Offset(
            pad + ((lng - minLng) / lngSpan).toFloat() * (size.width - 2 * pad),
            pad + (1f - ((lat - minLat) / latSpan).toFloat()) * (size.height - 2 * pad)
        )

        val a = point(order.pickupLat, order.pickupLng)
        val b = point(order.dropLat, order.dropLng)

        drawLine(
            color = lineColor,
            start = a,
            end = b,
            strokeWidth = 4.dp.toPx(),
            pathEffect = PathEffect.dashPathEffect(floatArrayOf(24f, 16f))
        )

        drawCircle(Orange, radius = 14.dp.toPx(), center = a)
        drawCircle(Color.White, radius = 5.dp.toPx(), center = a)
        drawCircle(Green, radius = 14.dp.toPx(), center = b)
        drawCircle(Color.White, radius = 5.dp.toPx(), center = b)
    }
}