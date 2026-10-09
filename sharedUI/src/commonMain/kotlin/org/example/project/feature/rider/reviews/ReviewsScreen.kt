package org.example.project.feature.rider.reviews

import androidx.compose.foundation.layout.*
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.example.project.components.*
import org.example.project.model.SampleData
import org.example.project.theme.*

@Composable
fun ReviewsScreen(onBack: () -> Unit) {
    val reviews = SampleData.reviews
    val avg = reviews.map { it.rating }.average()
    val avgText = (kotlin.math.round(avg * 10) / 10.0).toString()

    ScreenScaffold(title = "Ratings & reviews", onBack = onBack) {
        ContentCard {
            Column(Modifier.fillMaxWidth().padding(16.dp)) {
                Text("$avgText \u2B50", fontSize = 30.sp, fontWeight = FontWeight.Bold)
                Text("${reviews.size} ta review", color = GreyText, fontSize = 13.sp)
            }
        }
        Spacer(Modifier.height(20.dp))
        SectionLabel("Customer feedback")
        ContentCard {
            Column(Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
                reviews.forEachIndexed { i, r ->
                    if (i > 0) HorizontalDivider()
                    Column(Modifier.fillMaxWidth().padding(vertical = 12.dp)) {
                        Row {
                            Text(r.customer, fontWeight = FontWeight.SemiBold, fontSize = 14.sp, modifier = Modifier.weight(1f))
                            Text(r.date, color = GreyText, fontSize = 12.sp)
                        }
                        Text("\u2605".repeat(r.rating) + "\u2606".repeat(5 - r.rating), color = Orange, fontSize = 14.sp)
                        Spacer(Modifier.height(2.dp))
                        Text(r.comment, fontSize = 13.sp)
                    }
                }
            }
        }
    }
}