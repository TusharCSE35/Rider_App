package org.example.project.feature.order.cancel

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.example.project.model.CancelReason
import org.example.project.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CancelOrderSheet(
    onDismiss: () -> Unit,
    onConfirm: (CancelReason) -> Unit
) {
    var selected by remember { mutableStateOf<CancelReason?>(null) }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        containerColor = MaterialTheme.colorScheme.surface
    ) {
        Column(
            Modifier
                .fillMaxWidth()
                .navigationBarsPadding()
                .padding(horizontal = 20.dp)
                .padding(bottom = 20.dp)
        ) {
            Text("Cancel order", fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Text("Select a reason", color = GreyText, fontSize = 13.sp)
            Spacer(Modifier.height(12.dp))

            CancelReason.entries.forEach { reason ->
                Row(
                    Modifier
                        .fillMaxWidth()
                        .clickable { selected = reason }
                        .padding(vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(selected = selected == reason, onClick = { selected = reason })
                    Text(reason.label, fontSize = 15.sp)
                }
            }

            Spacer(Modifier.height(16.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                OutlinedButton(
                    onClick = onDismiss,
                    shape = RoundedCornerShape(14.dp),
                    modifier = Modifier.weight(1f).height(52.dp)
                ) { Text("Back") }
                Button(
                    onClick = { selected?.let(onConfirm) },
                    enabled = selected != null,
                    colors = ButtonDefaults.buttonColors(containerColor = androidx.compose.ui.graphics.Color(0xFFD93025)),
                    shape = RoundedCornerShape(14.dp),
                    modifier = Modifier.weight(1.4f).height(52.dp)
                ) { Text("Cancel order", fontWeight = FontWeight.SemiBold) }
            }
        }
    }
}