package org.example.project.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

@Composable
fun CountdownTimer(
    totalSeconds: Int,
    onFinish: () -> Unit,
    modifier: Modifier = Modifier,
    size: Dp = 60.dp
) {
    var remaining by remember { mutableStateOf(totalSeconds) }
    val finish by rememberUpdatedState(onFinish)

    LaunchedEffect(Unit) {
        while (remaining > 0) {
            delay(1000)
            remaining--
        }
        finish()
    }

    val progress by animateFloatAsState(
        targetValue = remaining / totalSeconds.toFloat(),
        animationSpec = tween(1000, easing = LinearEasing)
    )
    val color = if (remaining <= 10) Color(0xFFD93025) else MaterialTheme.colorScheme.primary

    Box(modifier.size(size), contentAlignment = Alignment.Center) {
        CircularProgressIndicator(
            progress = { progress },
            modifier = Modifier.size(size),
            color = color,
            trackColor = color.copy(alpha = 0.15f),
            strokeWidth = 4.dp
        )
        Text("${remaining}s", color = color, fontSize = 15.sp, fontWeight = FontWeight.Bold)
    }
}