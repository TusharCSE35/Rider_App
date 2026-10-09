package org.example.project.feature.rider.shift

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.example.project.components.*
import org.example.project.model.SampleData
import org.example.project.theme.*

@Composable
fun ShiftScheduleScreen(onBack: () -> Unit) {
    val toast = LocalToast.current
    var booked by remember { mutableStateOf(setOf("S2")) }

    ScreenScaffold(title = "Shift schedule", onBack = onBack) {
        Text(
            "Select the time slot you want to be online. Earn extra bonuses during peak hours.",
            color = GreyText, fontSize = 13.sp
        )
        Spacer(Modifier.height(4.dp))
        Text("${booked.size} slots selected", fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
        Spacer(Modifier.height(12.dp))

        SampleData.shifts.forEach { s ->
            ContentCard {
                Row(Modifier.fillMaxWidth().padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                    Column(Modifier.weight(1f)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(s.label, fontWeight = FontWeight.Bold, fontSize = 15.sp)
                            if (s.isPeak) {
                                Spacer(Modifier.width(8.dp))
                                StatusChip("Peak", Orange)
                            }
                        }
                        Text(s.time, color = GreyText, fontSize = 13.sp)
                        if (s.bonus > 0) {
                            Text("+\u09F3 ${s.bonus} bonus", color = Green, fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                        }
                    }
                    Switch(
                        checked = s.id in booked,
                        onCheckedChange = { on ->
                            booked = if (on) booked + s.id else booked - s.id
                            toast.info(if (on) "${s.label} slot added" else "${s.label} slot removed")
                        }
                    )
                }
            }
            Spacer(Modifier.height(10.dp))
        }
    }
}