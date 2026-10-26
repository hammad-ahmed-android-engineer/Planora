package com.example.planora.util

import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.Locale

/**
 * Extension functions for date and time calculations, formatting, and display.
 */

fun String?.formatIsoDateToDisplay(): String {
    if (this.isNullOrBlank()) return "No Due Date"
    return try {
        val date = LocalDate.parse(this)
        val today = LocalDate.now()
        val daysBetween = ChronoUnit.DAYS.between(today, date)

        when (daysBetween) {
            0L -> "Today"
            1L -> "Tomorrow"
            -1L -> "Yesterday"
            in 2..6 -> date.format(DateTimeFormatter.ofPattern("EEEE", Locale.getDefault()))
            else -> date.format(DateTimeFormatter.ofPattern("MMM d, yyyy", Locale.getDefault()))
        }
    } catch (_: Exception) {
        this
    }
}

fun String?.formatIsoTimeToDisplay(): String {
    if (this.isNullOrBlank()) return ""
    return try {
        val time = LocalTime.parse(this)
        time.format(DateTimeFormatter.ofPattern("h:mm a", Locale.getDefault()))
    } catch (_: Exception) {
        this
    }
}

fun Int.formatSecondsToMinSec(): String {
    val minutes = this / 60
    val seconds = this % 60
    return String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds)
}

fun greetingForTimeOfDay(): String {
    val hour = LocalTime.now().hour
    return when (hour) {
        in 5..11 -> "Good morning ☀️"
        in 12..16 -> "Good afternoon 🌤️"
        in 17..21 -> "Good evening 🌙"
        else -> "Good night 🌌"
    }
}
