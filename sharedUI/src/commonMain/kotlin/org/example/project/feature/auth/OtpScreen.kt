package org.example.project.feature.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Sms
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import org.example.project.components.LocalToast
import org.example.project.theme.*

private const val OTP_LENGTH = 4
private const val RESEND_SECONDS = 30
private const val DEV_OTP = "1234"
private val ErrorRed = Color(0xFFD93025)
private val BorderGrey = Color(0xFFDDDDDD)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OtpScreen(phone: String, onVerified: () -> Unit, onBack: () -> Unit) {
    val toast = LocalToast.current
    var otp by remember { mutableStateOf("") }
    var loading by remember { mutableStateOf(false) }
    var error by remember { mutableStateOf<String?>(null) }
    var seconds by remember { mutableStateOf(RESEND_SECONDS) }
    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(seconds) {
        if (seconds > 0) {
            delay(1000)
            seconds--
        }
    }

    LaunchedEffect(Unit) { focusRequester.requestFocus() }

    LaunchedEffect(loading) {
        if (loading) {
            delay(1200)
            if (otp == DEV_OTP) {
                toast.success("Login successful")
                onVerified()
            } else {
                error = "Invalid code. Please try again."
                toast.error("Invalid code. Please try again.")
                otp = ""
                loading = false
                focusRequester.requestFocus()
            }
        }
    }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Verification", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBack, enabled = !loading) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBackIosNew,
                            contentDescription = "Back",
                            modifier = Modifier.size(20.dp)
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White,
                    actionIconContentColor = Color.White
                )
            )
        }
    ) { inner ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(inner)
                .imePadding()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp, vertical = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(84.dp)
                    .background(MaterialTheme.colorScheme.primaryContainer, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Filled.Sms,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(40.dp)
                )
            }

            Spacer(Modifier.height(24.dp))
            Text(
                "Verify your number",
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(Modifier.height(10.dp))
            Text(
                text = buildAnnotatedString {
                    append("We sent a $OTP_LENGTH-digit code to\n")
                    withStyle(SpanStyle(fontWeight = FontWeight.SemiBold, color = DarkText)) {
                        append("+880 $phone")
                    }
                },
                color = GreyText,
                fontSize = 15.sp,
                lineHeight = 22.sp,
                textAlign = TextAlign.Center
            )
            Text(
                "Change number",
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.SemiBold,
                fontSize = 14.sp,
                modifier = Modifier
                    .padding(top = 6.dp)
                    .clickable(enabled = !loading) { onBack() }
                    .padding(8.dp)
            )

            Spacer(Modifier.height(28.dp))

            BasicTextField(
                value = otp,
                onValueChange = { new ->
                    if (!loading && new.length <= OTP_LENGTH && new.all { it.isDigit() }) {
                        otp = new
                        error = null
                    }
                },
                enabled = !loading,
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(focusRequester),
                decorationBox = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        repeat(OTP_LENGTH) { index ->
                            val char = otp.getOrNull(index)?.toString() ?: ""
                            val active = index == otp.length && !loading
                            val hasError = error != null
                            val borderColor = when {
                                hasError -> ErrorRed
                                active -> MaterialTheme.colorScheme.primary
                                else -> BorderGrey
                            }
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .height(68.dp)
                                    .background(
                                        if (char.isNotEmpty()) MaterialTheme.colorScheme.primaryContainer
                                        else MaterialTheme.colorScheme.surface,
                                        RoundedCornerShape(14.dp)
                                    )
                                    .border(
                                        if (active || hasError) 2.dp else 1.dp,
                                        borderColor,
                                        RoundedCornerShape(14.dp)
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    char,
                                    fontSize = 28.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            }
                        }
                    }
                }
            )

            error?.let {
                Spacer(Modifier.height(12.dp))
                Text(it, color = ErrorRed, fontSize = 13.sp)
            }

            Spacer(Modifier.height(24.dp))

            if (seconds > 0) {
                val mm = (seconds / 60).toString().padStart(2, '0')
                val ss = (seconds % 60).toString().padStart(2, '0')
                Text("Resend code in $mm:$ss", color = GreyText, fontSize = 14.sp)
            } else {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("Didn't receive the code? ", color = GreyText, fontSize = 14.sp)
                    Text(
                        "Resend",
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 14.sp,
                        modifier = Modifier.clickable(enabled = !loading) {
                            otp = ""
                            error = null
                            seconds = RESEND_SECONDS
                            toast.info("A new code has been sent")
                        }
                    )
                }
            }

            Spacer(Modifier.height(32.dp))

            Button(
                onClick = { loading = true },
                enabled = otp.length == OTP_LENGTH && !loading,
                shape = RoundedCornerShape(14.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp)
            ) {
                if (loading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        color = MaterialTheme.colorScheme.onPrimary,
                        strokeWidth = 2.5.dp
                    )
                } else {
                    Text("Verify & Login", fontSize = 17.sp, fontWeight = FontWeight.SemiBold)
                }
            }
        }
    }
}