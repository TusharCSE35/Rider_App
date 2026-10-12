package org.example.project

import androidx.compose.runtime.*
import org.example.project.components.ProvideToast
import org.example.project.core.storage.TokenStorage
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
                    onFinished = {
                        screen = if (TokenStorage.isLoggedIn) Screen.Home else Screen.Login
                    }
                )
                Screen.Login -> LoginScreen(
                    onSendOtp = { phone -> screen = Screen.Otp(phone) }
                )
                is Screen.Otp -> OtpScreen(
                    phone = s.phone,
                    onVerified = {
                        TokenStorage.saveSession(token = "dev-token", phone = s.phone)
                        screen = Screen.Home
                    },
                    onBack = { screen = Screen.Login }
                )
                Screen.Home -> HomeScreen(
                    onLogout = {
                        TokenStorage.clear()
                        screen = Screen.Login
                    }
                )
            }
        }
    }
}