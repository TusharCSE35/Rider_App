package org.example.project.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import org.example.project.theme.Green
import org.example.project.theme.Orange
import androidx.compose.material3.MaterialTheme

enum class ToastType { Success, Error, Info }

data class ToastData(
    val message: String,
    val type: ToastType,
    val durationMs: Long = 2500L,
    val id: Long = 0L
)

@Stable
class ToastState {
    var data by mutableStateOf<ToastData?>(null)
        private set
    var visible by mutableStateOf(false)
        private set

    private var counter = 0L

    fun show(message: String, type: ToastType = ToastType.Info, durationMs: Long = 2500L) {
        data = ToastData(message, type, durationMs, id = ++counter)
        visible = true
    }

    fun success(message: String) = show(message, ToastType.Success)
    fun error(message: String) = show(message, ToastType.Error, 3000L)
    fun info(message: String) = show(message, ToastType.Info)

    fun dismiss() {
        visible = false
    }
}

val LocalToast = staticCompositionLocalOf<ToastState> {
    error("ToastState not provided. Wrap your app with ProvideToast { }")
}

@Composable
fun ProvideToast(content: @Composable () -> Unit) {
    val state = remember { ToastState() }
    CompositionLocalProvider(LocalToast provides state) {
        Box(Modifier.fillMaxSize()) {
            content()
            ToastHost(state)
        }
    }
}

@Composable
private fun BoxScope.ToastHost(state: ToastState) {
    // restart timer whenever a new toast is shown
    LaunchedEffect(state.data?.id) {
        val d = state.data ?: return@LaunchedEffect
        delay(d.durationMs)
        state.dismiss()
    }

    Box(
        modifier = Modifier
            .align(Alignment.BottomCenter)
            .navigationBarsPadding()
            .imePadding()
            .padding(horizontal = 20.dp, vertical = 24.dp)
    ) {
        AnimatedVisibility(
            visible = state.visible,
            enter = slideInVertically { it / 2 } + fadeIn(),
            exit = slideOutVertically { it / 2 } + fadeOut()
        ) {
            state.data?.let { ToastCard(it, onClick = { state.dismiss() }) }
        }
    }
}

@Composable
private fun ToastCard(data: ToastData, onClick: () -> Unit) {
    val icon = when (data.type) {
        ToastType.Success -> Icons.Filled.CheckCircle
        ToastType.Error -> Icons.Filled.Error
        ToastType.Info -> Icons.Filled.Info
    }
    val container = when (data.type) {
        ToastType.Error -> Color(0xFFD93025)
        else -> MaterialTheme.colorScheme.primary
    }
    Surface(
        shape = RoundedCornerShape(16.dp),
        color = container,
        contentColor = MaterialTheme.colorScheme.onPrimary,
        shadowElevation = 8.dp,
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .background(Color.White.copy(alpha = 0.22f), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(icon, contentDescription = null, tint = Color.White, modifier = Modifier.size(20.dp))
            }
            Spacer(Modifier.width(12.dp))
            Text(
                data.message,
                color = Color.White,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.weight(1f)
            )
        }
    }
}