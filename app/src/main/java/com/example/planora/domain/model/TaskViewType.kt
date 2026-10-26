package com.example.planora.domain.model

enum class TaskViewType(val title: String) {
    INBOX("Inbox"),
    TODAY("Today"),
    UPCOMING("Upcoming"),
    NEXT_7_DAYS("Next 7 Days"),
    ALL_TASKS("All Tasks"),
    IN_PROGRESS("In Progress"),
    COMPLETED("Completed"),
    OVERDUE("Overdue"),
    NO_DUE_DATE("No Due Date"),
    HIGH_PRIORITY("High Priority"),
    FAVORITES("Favorites"),
    PINNED("Pinned"),
    RECENTLY_ADDED("Recently Added"),
    RECENTLY_COMPLETED("Recently Completed"),
    ARCHIVED("Archived"),
    TRASH("Trash")
}
