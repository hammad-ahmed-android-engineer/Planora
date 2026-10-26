package com.example.planora.domain.model

enum class RecurrenceType(val label: String) {
    NONE("None"),
    DAILY("Every Day"),
    WEEKDAYS("Every Weekday"),
    WEEKENDS("Every Weekend"),
    WEEKLY("Every Week"),
    MONTHLY("Every Month"),
    YEARLY("Every Year"),
    CUSTOM("Custom");

    companion object {
        fun fromString(value: String): RecurrenceType {
            return entries.firstOrNull { it.name.equals(value, ignoreCase = true) } ?: NONE
        }
    }
}
