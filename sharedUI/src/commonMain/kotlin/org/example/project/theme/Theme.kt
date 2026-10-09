package org.example.project.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val Orange = Color(0xFFFF6B00)
val OrangeLight = Color(0xFFFFE8D6)
val DarkText = Color(0xFF1C1B1F)
val GreyText = Color(0xFF6B6B6B)
val Green = Color(0xFF1FA45B)
val CardBorder = Color(0xFFEDE3DA)

@Composable
fun AppTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = lightColorScheme(
            primary = Orange,
            onPrimary = Color.White,
            primaryContainer = OrangeLight,
            onPrimaryContainer = Color(0xFF5A2600),
            background = Color(0xFFFFFBF8),
            surface = Color.White,
            onSurface = DarkText,
            onBackground = DarkText
        ),
        content = content
    )
}