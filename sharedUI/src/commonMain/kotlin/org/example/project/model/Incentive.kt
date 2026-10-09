package org.example.project.model

data class Incentive(
    val title: String,
    val description: String,
    val current: Int,
    val target: Int,
    val reward: Int
) {
    val progress: Float get() = (current / target.toFloat()).coerceIn(0f, 1f)
    val isDone: Boolean get() = current >= target
    val remaining: Int get() = (target - current).coerceAtLeast(0)
}