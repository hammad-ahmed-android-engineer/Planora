package com.example.planora.domain.model

enum class Priority(val level: Int, val label: String, val colorHex: String) {
    NONE(0, "None", "#808080"),
    LOW(1, "Low", "#34A853"),
    MEDIUM(2, "Medium", "#FBBC05"),
    HIGH(3, "High", "#FF6D00"),
    URGENT(4, "Urgent", "#EA4335");

    companion object {
        fun fromString(value: String): Priority {
            return entries.firstOrNull { it.name.equals(value, ignoreCase = true) } ?: NONE
        }
    }
}
