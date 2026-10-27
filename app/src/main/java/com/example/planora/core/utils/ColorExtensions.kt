package com.example.planora.core.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.core.graphics.toColorInt
import com.example.planora.core.designsystem.theme.IndigoPrimary
import com.example.planora.core.designsystem.theme.PriorityHigh
import com.example.planora.core.designsystem.theme.PriorityLow
import com.example.planora.core.designsystem.theme.PriorityMedium
import com.example.planora.core.designsystem.theme.PriorityNone
import com.example.planora.core.designsystem.theme.PriorityUrgent
import com.example.planora.domain.model.Priority

/**
 * Extension functions for color conversion, hex parsing, and priority colors.
 */

fun String?.toColor(fallback: Color = IndigoPrimary): Color {
    if (this.isNullOrBlank()) return fallback
    return try {
        Color(this.toColorInt())
    } catch (_: Exception) {
        fallback
    }
}

fun String?.parseHexColor(fallback: Color = Color.Unspecified): Color {
    if (this.isNullOrBlank()) return fallback
    val clean = this.trim().removePrefix("#")
    return try {
        when (clean.length) {
            3 -> {
                val r = clean.substring(0, 1).repeat(2).toInt(16)
                val g = clean.substring(1, 2).repeat(2).toInt(16)
                val b = clean.substring(2, 3).repeat(2).toInt(16)
                Color(r, g, b)
            }
            6 -> {
                val r = clean.substring(0, 2).toInt(16)
                val g = clean.substring(2, 4).toInt(16)
                val b = clean.substring(4, 6).toInt(16)
                Color(r, g, b)
            }
            8 -> {
                val a = clean.substring(0, 2).toInt(16)
                val r = clean.substring(2, 4).toInt(16)
                val g = clean.substring(4, 6).toInt(16)
                val b = clean.substring(6, 8).toInt(16)
                Color(r, g, b, a)
            }
            else -> fallback
        }
    } catch (_: Exception) {
        fallback
    }
}

val Priority.color: Color
    @Composable
    get() = when (this) {
        Priority.NONE -> PriorityNone
        Priority.LOW -> PriorityLow
        Priority.MEDIUM -> PriorityMedium
        Priority.HIGH -> PriorityHigh
        Priority.URGENT -> PriorityUrgent
    }
