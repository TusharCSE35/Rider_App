package org.example.project.feature.order.delivery

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.example.project.components.ContentCard
import org.example.project.components.LocalToast
import org.example.project.components.ScreenScaffold
import org.example.project.components.SectionLabel
import org.example.project.core.platform.rememberImagePicker
import org.example.project.model.Order
import org.example.project.theme.*

@Composable
fun DeliveryProofScreen(
    order: Order,
    onBack: () -> Unit,
    onSubmit: () -> Unit
) {
    val toast = LocalToast.current
    var photo by remember { mutableStateOf<ImageBitmap?>(null) }

    val picker = rememberImagePicker { result ->
        if (result != null) photo = result else toast.error("No photo has been taken.")
    }

    ScreenScaffold(title = "Delivery proof", onBack = onBack) {
        ContentCard {
            Column(Modifier.fillMaxWidth().padding(16.dp)) {
                Text(order.customer, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                Text(order.dropAddress, color = GreyText, fontSize = 13.sp)
            }
        }

        Spacer(Modifier.height(20.dp))
        SectionLabel("Delivery photo")
        Text(
            "Take a photo of the order being delivered to the customer's door or handed directly to them.",
            color = GreyText, fontSize = 13.sp
        )
        Spacer(Modifier.height(12.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(240.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(MaterialTheme.colorScheme.surfaceVariant),
            contentAlignment = Alignment.Center
        ) {
            val img = photo
            if (img != null) {
                Image(
                    bitmap = img,
                    contentDescription = "Delivery proof",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            } else {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("\uD83D\uDCF7", fontSize = 40.sp)
                    Spacer(Modifier.height(6.dp))
                    Text("No photo yet", color = GreyText, fontSize = 13.sp)
                }
            }
        }

        Spacer(Modifier.height(12.dp))
        OutlinedButton(
            onClick = { picker.launch() },
            shape = RoundedCornerShape(14.dp),
            modifier = Modifier.fillMaxWidth().height(50.dp)
        ) { Text(if (photo == null) "Take photo" else "Retake photo") }

        Spacer(Modifier.height(24.dp))
        Button(
            onClick = onSubmit,
            enabled = photo != null,
            shape = RoundedCornerShape(14.dp),
            modifier = Modifier.fillMaxWidth().height(54.dp)
        ) { Text("Submit & complete", fontSize = 16.sp, fontWeight = FontWeight.SemiBold) }
    }
}