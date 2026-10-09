package org.example.project.feature.drawer.screens

import androidx.compose.runtime.Composable

@Composable
fun PrivacyPolicyScreen(onBack: () -> Unit) = LegalDocumentScreen(
    title = "Privacy Policy",
    emoji = "\uD83D\uDD12",
    lastUpdated = "1 October 2026",
    intro = "Your privacy matters to us. This policy explains what information the Rider App collects and how we use it.",
    sections = listOf(
        LegalSection("Information we collect", "We collect your name, phone number, email, vehicle details, identity documents and, while you are online, your live location."),
        LegalSection("How we use it", "To assign and navigate orders, verify your identity, calculate and pay your earnings, and keep customers and riders safe."),
        LegalSection("Location data", "Location is used only while you are online or on an active delivery. You can turn it off, but you will not be able to receive orders."),
        LegalSection("Sharing", "We share limited details (name, photo, live location) with the customer and restaurant for your current order. We never sell your personal data."),
        LegalSection("Data retention", "We keep your data while your account is active and for the period required by law afterwards."),
        LegalSection("Your rights", "You can request a copy of your data, correct it, or delete your account from Settings or by contacting support.")
    ),
    onBack = onBack
)