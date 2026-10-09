package org.example.project.feature.earnings

import androidx.compose.runtime.Composable
import org.example.project.feature.earnings.cash.CashTrackingScreen
import org.example.project.feature.earnings.incentives.IncentivesScreen
import org.example.project.feature.earnings.tips.TipsBreakdownScreen
import org.example.project.feature.earnings.transactions.TransactionHistoryScreen
import org.example.project.feature.earnings.withdraw.WithdrawScreen

@Composable
fun EarningsDestinationScreen(destination: EarningsDestination, onBack: () -> Unit) {
    when (destination) {
        EarningsDestination.CASH -> CashTrackingScreen(onBack)
        EarningsDestination.WITHDRAW -> WithdrawScreen(onBack)
        EarningsDestination.TRANSACTIONS -> TransactionHistoryScreen(onBack)
        EarningsDestination.INCENTIVES -> IncentivesScreen(onBack)
        EarningsDestination.TIPS -> TipsBreakdownScreen(onBack)
    }
}