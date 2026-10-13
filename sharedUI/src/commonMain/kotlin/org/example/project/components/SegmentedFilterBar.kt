package org.example.project.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.example.project.theme.GreyText

/**
 * Shared segmented filter container used consistently across modules
 * (Earnings, Orders, ...).
 *
 * A single rounded container holding equal-width sections in one
 * horizontal row. The selected section is filled with the primary color,
 * unselected sections are transparent. Build sections with
 * [SegmentTextCell] and [SegmentIconCell] so every screen looks the same.
 */
@Composable
fun SegmentedFilterBar(
    modifier: Modifier = Modifier,
    content: @Composable RowScope.() -> Unit
) {
    Surface(
        shape = RoundedCornerShape(16.dp),
        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.55f),
        modifier = modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(4.dp),
            verticalAlignment = Alignment.CenterVertically,
            content = content
        )
    }
}

/** One equal-width text section inside [SegmentedFilterBar]. */
@Composable
fun RowScope.SegmentTextCell(
    label: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .weight(1f)
            .clip(RoundedCornerShape(12.dp))
            .background(
                if (selected) MaterialTheme.colorScheme.primary
                else Color.Transparent
            )
            .clickable { onClick() }
            .padding(vertical = 10.dp, horizontal = 4.dp)
    ) {
        Text(
            label,
            fontSize = 12.sp,
            maxLines = 1,
            fontWeight = if (selected) FontWeight.Bold else FontWeight.Medium,
            color = if (selected) Color.White else GreyText
        )
    }
}

/** One equal-width icon section inside [SegmentedFilterBar]. */
@Composable
fun RowScope.SegmentIconCell(
    selected: Boolean,
    onClick: () -> Unit,
    icon: @Composable (tint: Color) -> Unit
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .weight(1f)
            .clip(RoundedCornerShape(12.dp))
            .background(
                if (selected) MaterialTheme.colorScheme.primary
                else Color.Transparent
            )
            .clickable { onClick() }
            .padding(vertical = 8.dp, horizontal = 4.dp)
    ) {
        icon(if (selected) Color.White else GreyText)
    }
}

/**
 * Custom search-style filter icon: magnifying glass with filter lines
 * inside the lens, drawn with Canvas so it reads as both search and filter.
 */
@Composable
fun SearchFilterIcon(
    tint: Color,
    modifier: Modifier = Modifier
) {
    Canvas(modifier = modifier.size(22.dp)) {
        val stroke = 2.2.dp.toPx()
        val r = size.minDimension * 0.30f
        val cx = size.width * 0.42f
        val cy = size.height * 0.42f
        // Lens
        drawCircle(
            color = tint,
            radius = r,
            center = androidx.compose.ui.geometry.Offset(cx, cy),
            style = androidx.compose.ui.graphics.drawscope.Stroke(width = stroke)
        )
        // Handle
        val hx = cx + r * 0.72f
        val hy = cy + r * 0.72f
        drawLine(
            color = tint,
            start = androidx.compose.ui.geometry.Offset(hx, hy),
            end = androidx.compose.ui.geometry.Offset(size.width * 0.95f, size.height * 0.95f),
            strokeWidth = stroke,
            cap = androidx.compose.ui.graphics.StrokeCap.Round
        )
        // Filter lines inside the lens
        val lineWidths = listOf(0.55f, 0.80f, 0.42f)
        lineWidths.forEachIndexed { i, w ->
            val y = cy - r * 0.35f + i * r * 0.38f
            val half = r * w
            drawLine(
                color = tint,
                start = androidx.compose.ui.geometry.Offset(cx - half, y),
                end = androidx.compose.ui.geometry.Offset(cx + half, y),
                strokeWidth = stroke * 0.7f,
                cap = androidx.compose.ui.graphics.StrokeCap.Round
            )
        }
    }
}
