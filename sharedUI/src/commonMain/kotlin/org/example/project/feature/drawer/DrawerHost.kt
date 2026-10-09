package org.example.project.feature.drawer

import androidx.compose.runtime.Composable
import org.example.project.feature.drawer.screens.*

@Composable
fun DrawerDestinationScreen(
    destination: DrawerDestination,
    onBack: () -> Unit,
    onUnreadChange: (Int) -> Unit = {}
) {
    when (destination) {
        DrawerDestination.NOTIFICATIONS -> NotificationsScreen(onBack, onUnreadChange)
        DrawerDestination.SETTINGS -> SettingsScreen(onBack)
        DrawerDestination.HELP -> HelpSupportScreen(onBack)
        DrawerDestination.PRIVACY -> PrivacyPolicyScreen(onBack)
        DrawerDestination.TERMS -> TermsScreen(onBack)
        DrawerDestination.ABOUT -> AboutScreen(onBack)
    }
}