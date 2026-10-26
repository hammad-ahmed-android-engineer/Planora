package com.example.planora.domain.model

enum class TaskSortOption(val label: String) {
    DUE_DATE("Due Date"),
    PRIORITY("Priority"),
    TITLE("Alphabetical"),
    CREATION_DATE("Creation Date"),
    LAST_MODIFIED("Last Modified"),
    ESTIMATED_DURATION("Duration")
}
