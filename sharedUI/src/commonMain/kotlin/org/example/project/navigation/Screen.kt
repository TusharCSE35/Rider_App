package org.example.project.navigation

sealed interface Screen {
    data object Splash : Screen
    data object Login : Screen
    data class Otp(val phone: String) : Screen
    data object Home : Screen
}