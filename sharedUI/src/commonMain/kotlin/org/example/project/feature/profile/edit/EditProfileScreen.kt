package org.example.project.feature.profile.edit

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.example.project.components.*

@Composable
fun EditProfileScreen(onBack: () -> Unit) {
    val toast = LocalToast.current
    var name by remember { mutableStateOf("Tushar Sarkar") }
    var phone by remember { mutableStateOf("01700000000") }
    var vehicle by remember { mutableStateOf("DHAKA METRO-LA 12-3456") }
    var emergency by remember { mutableStateOf("") }

    val valid = name.isNotBlank() && phone.length == 11 && phone.startsWith("01")

    ScreenScaffold(title = "Edit profile", onBack = onBack) {
        OutlinedTextField(
            value = name, onValueChange = { name = it }, label = { Text("Full name") },
            singleLine = true, shape = RoundedCornerShape(14.dp), modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(12.dp))
        OutlinedTextField(
            value = phone, onValueChange = { phone = it.filter { c -> c.isDigit() }.take(11) },
            label = { Text("Phone") }, singleLine = true,
            isError = phone.isNotEmpty() && !valid,
            supportingText = { Text("01XXXXXXXXX (11 digit)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            shape = RoundedCornerShape(14.dp), modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(12.dp))
        OutlinedTextField(
            value = vehicle, onValueChange = { vehicle = it }, label = { Text("Vehicle number") },
            singleLine = true, shape = RoundedCornerShape(14.dp), modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(12.dp))
        OutlinedTextField(
            value = emergency, onValueChange = { emergency = it.filter { c -> c.isDigit() }.take(11) },
            label = { Text("Emergency contact (optional)") }, singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            shape = RoundedCornerShape(14.dp), modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(24.dp))
        Button(
            onClick = { toast.success("Profile updated"); onBack() },
            enabled = valid,
            shape = RoundedCornerShape(14.dp),
            modifier = Modifier.fillMaxWidth().height(54.dp)
        ) { Text("Save changes", fontSize = 16.sp, fontWeight = FontWeight.SemiBold) }
    }
}