package org.example.project.model

data class PerformanceStats(
    val acceptanceRate: Int,
    val onTimeRate: Int,
    val cancellationRate: Int,
    val completed: Int,
    val rating: Double
)