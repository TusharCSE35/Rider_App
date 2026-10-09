package org.example.project.feature.earnings.withdraw

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.example.project.components.*
import org.example.project.model.SampleData
import org.example.project.model.TransactionType
import org.example.project.model.WithdrawMethod
import org.example.project.theme.*

private const val MIN_WITHDRAW = 100

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WithdrawScreen(onBack: () -> Unit) {
    val toast = LocalToast.current
    var balance by remember { mutableStateOf(SampleData.walletBalance) }
    var method by remember { mutableStateOf(WithdrawMethod.BKASH) }
    var account by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }

    val amountInt = amount.toIntOrNull() ?: 0
    val amountError = amount.isNotEmpty() && (amountInt < MIN_WITHDRAW || amountInt > balance)
    val canSubmit = account.length >= 6 && amountInt in MIN_WITHDRAW..balance

    ScreenScaffold(title = "Withdraw", onBack = onBack) {
        ContentCard {
            Column(Modifier.fillMaxWidth().padding(16.dp)) {
                Text("Available balance", color = GreyText, fontSize = 13.sp)
                Text("\u09F3 $balance", fontSize = 28.sp, fontWeight = FontWeight.Bold, color = Green)
            }
        }

        Spacer(Modifier.height(20.dp))
        SectionLabel("Method")
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            WithdrawMethod.entries.forEach { m ->
                FilterChip(selected = method == m, onClick = { method = m }, label = { Text(m.label) })
            }
        }

        Spacer(Modifier.height(16.dp))
        OutlinedTextField(
            value = account,
            onValueChange = { account = it.filter { c -> c.isDigit() }.take(20) },
            label = { Text(method.hint) },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            shape = RoundedCornerShape(14.dp),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(12.dp))
        OutlinedTextField(
            value = amount,
            onValueChange = { amount = it.filter { c -> c.isDigit() }.take(7) },
            label = { Text("Amount (\u09F3)") },
            singleLine = true,
            isError = amountError,
            supportingText = { Text(if (amountError) "Min \u09F3 $MIN_WITHDRAW, max \u09F3 $balance" else "Minimum \u09F3 $MIN_WITHDRAW") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            shape = RoundedCornerShape(14.dp),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(16.dp))
        Button(
            onClick = {
                balance -= amountInt
                toast.success("\u09F3 $amountInt ${method.label}-e withdrawal request has been submitted")
                amount = ""
            },
            enabled = canSubmit,
            shape = RoundedCornerShape(14.dp),
            modifier = Modifier.fillMaxWidth().height(54.dp)
        ) { Text("Withdraw", fontSize = 16.sp, fontWeight = FontWeight.SemiBold) }

        Spacer(Modifier.height(24.dp))
        SectionLabel("Withdraw history")
        ContentCard {
            Column(Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
                val payouts = SampleData.transactions.filter { it.type == TransactionType.PAYOUT }
                payouts.forEachIndexed { i, t ->
                    if (i > 0) HorizontalDivider()
                    Row(Modifier.fillMaxWidth().padding(vertical = 12.dp), verticalAlignment = Alignment.CenterVertically) {
                        Column(Modifier.weight(1f)) {
                            Text(t.title, fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
                            Text(t.date, color = GreyText, fontSize = 12.sp)
                        }
                        Text("\u09F3 ${-t.amount}", fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}