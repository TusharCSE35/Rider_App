package org.example.project.model

data class LeaderboardEntry(
    val rank: Int,

    val name: String,

    val orders: Int,
    val isMe: Boolean = false
)