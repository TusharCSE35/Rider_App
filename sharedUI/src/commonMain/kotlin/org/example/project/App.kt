package org.example.project

import androidx.compose.runtime.*
import org.example.project.components.ProvideToast
import org.example.project.navigation.Screen
import org.example.project.theme.AppTheme
import org.example.project.feature.splash.SplashScreen
import org.example.project.feature.auth.LoginScreen
import org.example.project.feature.auth.OtpScreen
import org.example.project.feature.home.HomeScreen

@Composable
fun App() {
    AppTheme {
        ProvideToast {
            var screen by remember { mutableStateOf<Screen>(Screen.Splash) }

            when (val s = screen) {
                Screen.Splash -> SplashScreen(
                    onFinished = { screen = Screen.Login }
                )
                Screen.Login -> LoginScreen(
                    onSendOtp = { phone -> screen = Screen.Otp(phone) }
                )
                is Screen.Otp -> OtpScreen(
                    phone = s.phone,
                    onVerified = { screen = Screen.Home },
                    onBack = { screen = Screen.Login }
                )
                Screen.Home -> HomeScreen(
                    onLogout = { screen = Screen.Login }
                )
            }
        }
    }
}