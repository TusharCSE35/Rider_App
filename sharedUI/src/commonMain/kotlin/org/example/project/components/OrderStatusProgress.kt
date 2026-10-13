package org.example.project.components

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.example.project.model.OrderStatus
import org.example.project.theme.Green
import org.example.project.theme.GreyText
import org.example.project.theme.Orange

private val progressSteps = listOf(
    OrderStatus.ACCEPTED,
    OrderStatus.PICKED_UP,
    OrderStatus.ON_THE_WAY,
    OrderStatus.DELIVERED
)

private fun stepLabel(status: OrderStatus): String = when (status) {
    OrderStatus.ACCEPTED -> "Accepted"
    OrderStatus.PICKED_UP -> "Picked Up"
    OrderStatus.ON_THE_WAY -> "On the Way"
    OrderStatus.DELIVERED -> "Delivered"
    OrderStatus.CANCELLED -> "Cancelled"
}

/**
 * Order status progression in a SINGLE horizontal row:
 * Accepted → Picked Up → On the Way → Delivered.
 *
 * One outer [Row] only (horizontal scroll fallback on narrow screens, never
 * stacked vertically). Each step is dot + label inline, joined by → chevrons.
 * Completed steps are green checks, current step is highlighted orange/green,
 * upcoming steps are muted. Cancelled renders a single horizontal state.
 */
@Composable
fun OrderStatusProgress(
    status: OrderStatus,
    modifier: Modifier = Modifier
) {
    ContentCard(modifier = modifier) {
        if (status == OrderStatus.CANCELLED) {
            Row(
                Modifier.fillMaxWidth().padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Box(
                    Modifier.size(10.dp).clip(CircleShape)
                        .background(Color(0xFFD32F2F))
                )
                Spacer(Modifier.width(8.dp))
                Text(
                    "Order cancelled",
                    color = Color(0xFFD32F2F),
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    maxLines = 1
                )
            }
            return@ContentCard
        }

        val currentIndex = progressSteps.indexOf(status).coerceAtLeast(0)

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState())
                .padding(horizontal = 14.dp, vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            progressSteps.forEachIndexed { index, step ->
                val done = index < currentIndex
                val current = index == currentIndex
                val dotColor = when {
                    done -> Green
                    current -> if (step == OrderStatus.DELIVERED) Green else Orange
                    else -> GreyText.copy(alpha = 0.30f)
                }
                val labelColor = when {
                    done || current -> MaterialTheme.colorScheme.onSurface
                    else -> GreyText
                }
                // Step: dot + label inline in the same row
                Box(
                    Modifier.size(if (current) 22.dp else 18.dp).clip(CircleShape)
                        .background(dotColor),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        if (done) "✓" else "${index + 1}",
                        color = Color.White,
                        fontSize = if (current) 11.sp else 10.sp,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1
                    )
                }
                Spacer(Modifier.width(6.dp))
                Text(
                    stepLabel(step),
                    fontSize = 12.sp,
                    maxLines = 1,
                    fontWeight = if (current) FontWeight.Bold else FontWeight.Medium,
                    color = labelColor
                )
                // Chevron separator between steps (same row)
                if (index < progressSteps.lastIndex) {
                    Spacer(Modifier.width(8.dp))
                    Text(
                        "→",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        color = if (index < currentIndex) Green else GreyText.copy(alpha = 0.45f)
                    )
                    Spacer(Modifier.width(8.dp))
                }
            }
        }
    }
}
