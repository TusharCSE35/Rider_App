package org.example.project.feature.rider

import androidx.compose.runtime.Composable
import org.example.project.feature.profile.documents.DocumentsScreen
import org.example.project.feature.profile.edit.EditProfileScreen
import org.example.project.feature.rider.leaderboard.BadgesScreen
import org.example.project.feature.rider.leaderboard.LeaderboardScreen
import org.example.project.feature.rider.performance.PerformanceScreen
import org.example.project.feature.rider.reviews.ReviewsScreen
import org.example.project.feature.rider.shift.ShiftScheduleScreen
import org.example.project.feature.rider.zones.HotZonesScreen
import org.example.project.feature.safety.IncidentReportScreen

@Composable
fun RiderDestinationScreen(destination: RiderDestination, onBack: () -> Unit) {
    when (destination) {
        RiderDestination.SHIFT -> ShiftScheduleScreen(onBack)
        RiderDestination.HOT_ZONES -> HotZonesScreen(onBack)
        RiderDestination.PERFORMANCE -> PerformanceScreen(onBack)
        RiderDestination.REVIEWS -> ReviewsScreen(onBack)
        RiderDestination.LEADERBOARD -> LeaderboardScreen(onBack)
        RiderDestination.BADGES -> BadgesScreen(onBack)
        RiderDestination.EDIT_PROFILE -> EditProfileScreen(onBack)
        RiderDestination.DOCUMENTS -> DocumentsScreen(onBack)
        RiderDestination.INCIDENT -> IncidentReportScreen(onBack)
    }
}