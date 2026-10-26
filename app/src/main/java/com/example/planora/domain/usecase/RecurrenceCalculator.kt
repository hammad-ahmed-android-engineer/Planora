package com.example.planora.domain.usecase

import com.example.planora.domain.model.RecurrenceType
import com.example.planora.domain.model.Task
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter

object RecurrenceCalculator {

    private val dateFormatter = DateTimeFormatter.ISO_LOCAL_DATE

    fun calculateNextDueDate(task: Task, completionDate: LocalDate = LocalDate.now()): String? {
        if (task.recurrenceType == RecurrenceType.NONE) return null

        val baseDate = if (task.repeatAfterCompletion) {
            completionDate
        } else {
            task.dueDate?.let {
                try {
                    LocalDate.parse(it, dateFormatter)
                } catch (_: Exception) {
                    completionDate
                }
            } ?: completionDate
        }

        val interval = task.recurrenceInterval.coerceAtLeast(1).toLong()

        val nextDate: LocalDate = when (task.recurrenceType) {
            RecurrenceType.DAILY -> baseDate.plusDays(interval)
            RecurrenceType.WEEKDAYS -> getNextWeekday(baseDate, interval)
            RecurrenceType.WEEKENDS -> getNextWeekend(baseDate)
            RecurrenceType.WEEKLY -> baseDate.plusWeeks(interval)
            RecurrenceType.MONTHLY -> baseDate.plusMonths(interval)
            RecurrenceType.YEARLY -> baseDate.plusYears(interval)
            RecurrenceType.CUSTOM -> getNextCustomDay(baseDate, task.recurrenceDaysOfWeek, interval)
            RecurrenceType.NONE -> return null
        }

        // Check if next date exceeds recurrence end timestamp
        task.recurrenceEndTimestamp?.let { endMs ->
            val nextMs = nextDate.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
            if (nextMs > endMs) return null
        }

        return nextDate.format(dateFormatter)
    }

    private fun getNextWeekday(baseDate: LocalDate, intervalDays: Long): LocalDate {
        var date = baseDate.plusDays(intervalDays)
        while (date.dayOfWeek == DayOfWeek.SATURDAY || date.dayOfWeek == DayOfWeek.SUNDAY) {
            date = date.plusDays(1)
        }
        return date
    }

    private fun getNextWeekend(baseDate: LocalDate): LocalDate {
        var date = baseDate.plusDays(1)
        while (date.dayOfWeek != DayOfWeek.SATURDAY && date.dayOfWeek != DayOfWeek.SUNDAY) {
            date = date.plusDays(1)
        }
        return date
    }

    private fun getNextCustomDay(baseDate: LocalDate, daysOfWeekStr: String?, intervalWeeks: Long): LocalDate {
        if (daysOfWeekStr.isNullOrEmpty()) return baseDate.plusWeeks(intervalWeeks)

        val targetDays = daysOfWeekStr.split(",")
            .mapNotNull { it.trim().toIntOrNull() } // 1 = Monday, 7 = Sunday
            .mapNotNull { dayNum ->
                try {
                    DayOfWeek.of(dayNum)
                } catch (_: Exception) {
                    null
                }
            }.toSet()

        if (targetDays.isEmpty()) return baseDate.plusWeeks(intervalWeeks)

        var candidate = baseDate.plusDays(1)
        for (step in 1..365) {
            if (candidate.dayOfWeek in targetDays) {
                return candidate
            }
            candidate = candidate.plusDays(1)
        }
        return baseDate.plusWeeks(intervalWeeks)
    }
}
