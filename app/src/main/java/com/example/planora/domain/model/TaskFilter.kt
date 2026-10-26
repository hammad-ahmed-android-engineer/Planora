package com.example.planora.domain.model

data class TaskFilter(
    val categoryId: String? = null,
    val priority: Priority? = null,
    val isCompleted: Boolean? = null,
    val isStarred: Boolean? = null,
    val isPinned: Boolean? = null,
    val isOverdue: Boolean? = null,
    val tagIds: Set<String> = emptySet(),
    val hasSubtasks: Boolean? = null,
    val hasReminders: Boolean? = null,
    val startDateRange: String? = null,
    val endDateRange: String? = null
)
