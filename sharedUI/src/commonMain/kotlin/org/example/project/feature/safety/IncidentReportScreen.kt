package org.example.project.feature.safety

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.example.project.components.*
import org.example.project.model.IncidentType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IncidentReportScreen(onBack: () -> Unit) {
    val toast = LocalToast.current
    var type by remember { mutableStateOf(IncidentType.ACCIDENT) }
    var details by remember { mutableStateOf("") }

    ScreenScaffold(title = "Report incident", onBack = onBack) {
        SectionLabel("What happened?")
        Row(Modifier.horizontalScroll(rememberScrollState()), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            IncidentType.entries.forEach { t ->
                FilterChip(selected = type == t, onClick = { type = t }, label = { Text(t.label) })
            }
        }

        Spacer(Modifier.height(16.dp))
        OutlinedTextField(
            value = details,
            onValueChange = { details = it },
            label = { Text("What happened? Write") },
            minLines = 4,
            shape = RoundedCornerShape(14.dp),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(24.dp))
        Button(
            onClick = {
                toast.success("The report has been submitted. The support team will contact you.")
                onBack()
            },
            enabled = details.trim().length >= 10,
            shape = RoundedCornerShape(14.dp),
            modifier = Modifier.fillMaxWidth().height(54.dp)
        ) { Text("Submit report", fontSize = 16.sp, fontWeight = FontWeight.SemiBold) }
    }
}