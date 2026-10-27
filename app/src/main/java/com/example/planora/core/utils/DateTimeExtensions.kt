package com.example.planora.core.utils

import com.example.planora.domain.model.RecurrenceType
import com.example.planora.domain.model.Task
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.Locale

private val DATE_FORMATTER: DateTimeFormatter = DateTimeFormatter.ISO_LOCAL_DATE

/**
 * Calculates the next due date for a recurring task based on its recurrence type, interval, and completion date.
 */
fun Task.calculateNextDueDate(completionDate: LocalDate = LocalDate.now()): String? {
    if (this.recurrenceType == RecurrenceType.NONE) return null

    val baseDate = if (this.repeatAfterCompletion) {
        completionDate
    } else {
        this.dueDate?.let {
            try {
                LocalDate.parse(it, DATE_FORMATTER)
            } catch (_: Exception) {
                completionDate
            }
        } ?: completionDate
    }

    val interval = this.recurrenceInterval.coerceAtLeast(1).toLong()

    val nextDate: LocalDate = when (this.recurrenceType) {
        RecurrenceType.DAILY -> baseDate.plusDays(interval)
        RecurrenceType.WEEKDAYS -> baseDate.getNextWeekday(interval)
        RecurrenceType.WEEKENDS -> baseDate.getNextWeekend()
        RecurrenceType.WEEKLY -> baseDate.plusWeeks(interval)
        RecurrenceType.MONTHLY -> baseDate.plusMonths(interval)
        RecurrenceType.YEARLY -> baseDate.plusYears(interval)
        RecurrenceType.CUSTOM -> baseDate.getNextCustomDay(this.recurrenceDaysOfWeek, interval)
        RecurrenceType.NONE -> return null
    }

    this.recurrenceEndTimestamp?.let { endMs ->
        val nextMs = nextDate.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
        if (nextMs > endMs) return null
    }

    return nextDate.format(DATE_FORMATTER)
}

private fun LocalDate.getNextWeekday(intervalDays: Long): LocalDate {
    var date = this.plusDays(intervalDays)
    while (date.dayOfWeek == DayOfWeek.SATURDAY || date.dayOfWeek == DayOfWeek.SUNDAY) {
        date = date.plusDays(1)
    }
    return date
}

private fun LocalDate.getNextWeekend(): LocalDate {
    var date = this.plusDays(1)
    while (date.dayOfWeek != DayOfWeek.SATURDAY && date.dayOfWeek != DayOfWeek.SUNDAY) {
        date = date.plusDays(1)
    }
    return date
}

private fun LocalDate.getNextCustomDay(daysOfWeekStr: String?, intervalWeeks: Long): LocalDate {
    if (daysOfWeekStr.isNullOrEmpty()) return this.plusWeeks(intervalWeeks)

    val targetDays = daysOfWeekStr.split(",")
        .mapNotNull { it.trim().toIntOrNull() }
        .mapNotNull { dayNum ->
            try {
                DayOfWeek.of(dayNum)
            } catch (_: Exception) {
                null
            }
        }.toSet()

    if (targetDays.isEmpty()) return this.plusWeeks(intervalWeeks)

    var candidate = this.plusDays(1)
    repeat(365) {
        if (candidate.dayOfWeek in targetDays) {
            return candidate
        }
        candidate = candidate.plusDays(1)
    }
    return this.plusWeeks(intervalWeeks)
}

/**
 * Finds the next occurrence of a specific DayOfWeek starting from today.
 */
fun LocalDate.nextDayOfWeek(dayOfWeek: DayOfWeek): LocalDate {
    var date = this.plusDays(1)
    while (date.dayOfWeek != dayOfWeek) {
        date = date.plusDays(1)
    }
    return date
}

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
