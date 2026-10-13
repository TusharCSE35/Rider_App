package org.example.project.components

import androidx.compose.ui.unit.dp

/**
 * Standard gaps keeping the Home, Orders and Earnings layouts consistent.
 *
 * Root cause of the previous drift: each screen hardcoded its own padding
 * literals (20dp vs 16dp below the AppBar, 12dp vs 16dp below filters, plus
 * the Orders list's own 20dp top content padding stacking on top of its
 * filter gap). Every screen below must use these values so the rhythm stays
 * identical everywhere.
 *
 * Note: the Profile screen keeps its own design and does not use these.
 */
object ScreenSpacing {
    /** Gap between the AppBar ([ScreenHeader]) and the content below it. */
    val belowHeader = 16.dp

    /** Gap between a filter row and the content below it. */
    val belowFilter = 12.dp

    /** Horizontal screen-edge padding for content. */
    val sides = 20.dp
}
