package org.example.project.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.example.project.theme.*

@Composable
fun ProgressBarCard(
    title: String,
    subtitle: String,
    progress: Float,
    trailing: String,
    color: Color = Green,
    modifier: Modifier = Modifier
) {
    ContentCard {
        Column(modifier.fillMaxWidth().padding(16.dp)) {
            Row(Modifier.fillMaxWidth()) {
                Column(Modifier.weight(1f)) {
                    Text(title, fontWeight = FontWeight.Bold, fontSize = 15.sp)
                    Text(subtitle, color = GreyText, fontSize = 13.sp)
                }
                Text(trailing, color = color, fontWeight = FontWeight.Bold, fontSize = 16.sp)
            }
            Spacer(Modifier.height(12.dp))
            LinearProgressIndicator(
                progress = { progress },
                color = color,
                trackColor = color.copy(alpha = 0.15f),
                modifier = Modifier.fillMaxWidth().height(8.dp).clip(RoundedCornerShape(50))
            )
        }
    }
}