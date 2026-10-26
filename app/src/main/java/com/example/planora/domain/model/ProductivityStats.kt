package com.example.planora.domain.model

data class ProductivityStats(
    val createdToday: Int = 0,
    val completedToday: Int = 0,
    val completedThisWeek: Int = 0,
    val completedThisMonth: Int = 0,
    val totalActive: Int = 0,
    val totalCompleted: Int = 0,
    val overdueCount: Int = 0,
    val completionRatePercentage: Float = 0f,
    val currentStreakDays: Int = 0,
    val longestStreakDays: Int = 0,
    val totalFocusMinutesToday: Int = 0,
    val totalFocusMinutesThisWeek: Int = 0,
    val categoryDistribution: Map<String, Int> = emptyMap(),
    val priorityDistribution: Map<Priority, Int> = emptyMap()
)
