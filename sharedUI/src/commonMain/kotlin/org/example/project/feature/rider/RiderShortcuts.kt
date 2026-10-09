package org.example.project.feature.rider

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.example.project.components.ContentCard
import org.example.project.theme.*

@Composable
fun RiderShortcuts(onOpen: (RiderDestination) -> Unit) {
    ContentCard {
        Column(Modifier.fillMaxWidth()) {
            RiderDestination.entries.forEachIndexed { i, d ->
                if (i > 0) HorizontalDivider()
                Row(
                    Modifier.fillMaxWidth().clickable { onOpen(d) }.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(d.emoji, fontSize = 20.sp)
                    Spacer(Modifier.width(12.dp))
                    Text(d.label, modifier = Modifier.weight(1f), fontWeight = FontWeight.Medium, fontSize = 15.sp)
                    Text("\u203A", color = GreyText, fontSize = 20.sp)
                }
            }
        }
    }
}