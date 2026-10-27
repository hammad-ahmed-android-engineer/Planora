package com.example.planora.core.utils

import com.example.planora.domain.model.RecurrenceType

/**
 * Extension functions for string validation and recurrence labels.
 */

fun String?.isValidTitle(): Boolean {
    return !this.isNullOrBlank() && this.trim().length <= 250
}

fun RecurrenceType.formatRecurrenceLabel(interval: Int = 1): String {
    return when (this) {
        RecurrenceType.NONE -> "Never"
        RecurrenceType.DAILY -> if (interval == 1) "Daily" else "Every $interval days"
        RecurrenceType.WEEKDAYS -> "Every weekday (Mon-Fri)"
        RecurrenceType.WEEKENDS -> "Every weekend"
        RecurrenceType.WEEKLY -> if (interval == 1) "Weekly" else "Every $interval weeks"
        RecurrenceType.MONTHLY -> if (interval == 1) "Monthly" else "Every $interval months"
        RecurrenceType.YEARLY -> if (interval == 1) "Yearly" else "Every $interval years"
        RecurrenceType.CUSTOM -> "Custom"
    }
}
