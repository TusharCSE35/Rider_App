package org.example.project.feature.profile.documents

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.example.project.components.*
import org.example.project.core.platform.rememberImagePicker
import org.example.project.model.DocStatus
import org.example.project.model.RiderDocument
import org.example.project.model.SampleData
import org.example.project.theme.*

@Composable
fun DocumentsScreen(onBack: () -> Unit) {
    val toast = LocalToast.current
    var docs by remember { mutableStateOf<List<RiderDocument>>(SampleData.documents) }
    var photos by remember { mutableStateOf<Map<String, ImageBitmap>>(emptyMap()) }
    var target by remember { mutableStateOf<String?>(null) }

    val picker = rememberImagePicker { bmp ->
        val name = target
        if (bmp != null && name != null) {
            photos = photos + (name to bmp)
            docs = docs.map { if (it.name == name) it.copy(status = DocStatus.PENDING) else it }
            toast.success("$name has been uploaded and submitted for review")
        } else if (bmp == null) {
            toast.error("Photo neya hoy ni")
        }
    }

    ScreenScaffold(title = "Documents", onBack = onBack) {
        Text("You will receive your payout once all documents have been verified.", color = GreyText, fontSize = 13.sp)
        Spacer(Modifier.height(12.dp))

        docs.forEach { d ->
            val color = when (d.status) {
                DocStatus.VERIFIED -> Green
                DocStatus.PENDING -> Orange
                DocStatus.REJECTED -> Color(0xFFD93025)
                DocStatus.MISSING -> GreyText
            }
            ContentCard {
                Column(Modifier.fillMaxWidth().padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(d.name, fontWeight = FontWeight.Bold, fontSize = 15.sp, modifier = Modifier.weight(1f))
                        StatusChip(d.status.label, color)
                    }
                    photos[d.name]?.let { img ->
                        Spacer(Modifier.height(10.dp))
                        Image(
                            bitmap = img,
                            contentDescription = d.name,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxWidth().height(140.dp).clip(RoundedCornerShape(12.dp))
                        )
                    }
                    if (d.status == DocStatus.REJECTED) {
                        Spacer(Modifier.height(6.dp))
                        Text("The photo was blurry. Please upload it again.", color = color, fontSize = 12.sp)
                    }
                    if (d.status != DocStatus.VERIFIED) {
                        Spacer(Modifier.height(10.dp))
                        OutlinedButton(
                            onClick = { target = d.name; picker.launch() },
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier.fillMaxWidth().height(44.dp)
                        ) {
                            Text(if (d.status == DocStatus.MISSING) "Upload" else "Re-upload")
                        }
                    }
                }
            }
            Spacer(Modifier.height(10.dp))
        }
    }
}