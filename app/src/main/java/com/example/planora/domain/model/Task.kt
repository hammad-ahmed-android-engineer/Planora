package com.example.planora.domain.model

import java.util.UUID

data class Task(
    val id: String = UUID.randomUUID().toString(),
    val title: String,
    val description: String = "",
    val createdTimestamp: Long = System.currentTimeMillis(),
    val lastModifiedTimestamp: Long = System.currentTimeMillis(),
    val dueDate: String? = null, // YYYY-MM-DD
    val dueTime: String? = null, // HH:mm
    val startDate: String? = null,
    val startTime: String? = null,
    val deadline: Long? = null,
    val priority: Priority = Priority.NONE,
    val category: Category? = null,
    val tags: List<Tag> = emptyList(),
    val subtasks: List<Subtask> = emptyList(),
    val reminders: List<Reminder> = emptyList(),
    val isStarred: Boolean = false,
    val isPinned: Boolean = false,
    val colorHex: String? = null,
    val estimatedDurationMinutes: Int = 0,
    val actualTimeSpentMinutes: Int = 0,
    val completionTimestamp: Long? = null,
    val isCompleted: Boolean = false,
    val isArchived: Boolean = false,
    val isDeleted: Boolean = false,
    val recurrenceType: RecurrenceType = RecurrenceType.NONE,
    val recurrenceInterval: Int = 1,
    val recurrenceDaysOfWeek: String? = null,
    val recurrenceEndTimestamp: Long? = null,
    val repeatAfterCompletion: Boolean = false,
    val locationReminder: String? = null,
    val parentTaskId: String? = null,
    val templateId: String? = null,
    val notes: String = ""
) {
    val subtaskProgress: Float
        get() {
            if (subtasks.isEmpty()) return 0f
            val completed = subtasks.count { it.isCompleted }
            return completed.toFloat() / subtasks.size
        }

    val isOverdue: Boolean
        get() {
            if (isCompleted || dueDate == null) return false
            val today = java.time.LocalDate.now().toString()
            return dueDate < today
        }
}
