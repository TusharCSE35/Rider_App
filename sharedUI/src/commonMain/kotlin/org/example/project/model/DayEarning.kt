package org.example.project.model

data class DayEarning(
    val day: String,

    val amount: Int,
    val orders: Int,
    /** Complete display date including day, month and year (e.g. "Sat, Oct 4, 2026"). */
    val fullDate: String = day
)