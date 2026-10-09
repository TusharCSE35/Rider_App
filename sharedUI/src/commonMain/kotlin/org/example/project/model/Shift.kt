package org.example.project.model

data class Shift(
    val id: String,
    val label: String,
    val time: String,
    val isPeak: Boolean = false,
    val bonus: Int = 0
)