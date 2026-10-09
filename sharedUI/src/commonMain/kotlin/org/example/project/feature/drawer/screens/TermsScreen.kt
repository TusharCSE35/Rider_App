package org.example.project.feature.drawer.screens

import androidx.compose.runtime.Composable

@Composable
fun TermsScreen(onBack: () -> Unit) = LegalDocumentScreen(
    title = "Terms & Conditions",
    emoji = "\uD83D\uDCC4",
    lastUpdated = "1 October 2026",
    intro = "By using the Rider App you agree to the following terms. Please read them carefully.",
    sections = listOf(
        LegalSection("Eligibility", "You must be at least 18, hold a valid driving license and vehicle registration, and pass identity verification."),
        LegalSection("Your responsibilities", "Deliver orders safely and on time, handle food hygienically, follow traffic laws, and treat customers and restaurant staff respectfully."),
        LegalSection("Earnings & payouts", "You earn a delivery fee per completed order plus any bonuses. Payouts are processed weekly to your registered account."),
        LegalSection("Cancellations", "Repeated cancellations or late deliveries may reduce your rating and can lead to temporary suspension."),
        LegalSection("Account suspension", "We may suspend or close accounts that violate these terms, provide false information, or put others at risk.")
    ),
    onBack = onBack
)