package com.example.planora.domain.usecase

import com.example.planora.domain.model.Priority
import com.example.planora.domain.model.ProductivityStats
import com.example.planora.domain.model.Task
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

import javax.inject.Inject

class CalculateProductivityStatsUseCase @Inject constructor() {

    fun execute(
        allActiveTasks: List<Task>,
        allCompletedTasks: List<Task>,
        focusMinutesToday: Int = 0,
        focusMinutesThisWeek: Int = 0
    ): ProductivityStats {
        val today = LocalDate.now()
        val zone = ZoneId.systemDefault()

        val allTasks = (allActiveTasks + allCompletedTasks).distinctBy { it.id }
        val activeTasks = allTasks.filter { !it.isCompleted }
        val completedTasks = allTasks.filter { it.isCompleted }

        val createdTodayCount = allTasks.count {
            Instant.ofEpochMilli(it.createdTimestamp).atZone(zone).toLocalDate() == today
        }

        val completedTodayCount = completedTasks.count {
            it.completionTimestamp?.let { ts ->
                Instant.ofEpochMilli(ts).atZone(zone).toLocalDate() == today
            } ?: false
        }

        val weekAgo = today.minusDays(7)
        val completedThisWeekCount = completedTasks.count {
            it.completionTimestamp?.let { ts ->
                val date = Instant.ofEpochMilli(ts).atZone(zone).toLocalDate()
                !date.isBefore(weekAgo)
            } ?: false
        }

        val monthAgo = today.minusMonths(1)
        val completedThisMonthCount = completedTasks.count {
            it.completionTimestamp?.let { ts ->
                val date = Instant.ofEpochMilli(ts).atZone(zone).toLocalDate()
                !date.isBefore(monthAgo)
            } ?: false
        }

        val totalActive = activeTasks.size
        val totalCompleted = completedTasks.size
        val totalAll = allTasks.size

        val rate = if (totalAll > 0) (totalCompleted.toFloat() / totalAll.toFloat()) * 100f else 0f

        val overdueCount = activeTasks.count { it.isOverdue }

        // Category distribution
        val catMap = mutableMapOf<String, Int>()
        allTasks.forEach { task ->
            val catName = task.category?.name ?: "Uncategorized"
            catMap[catName] = (catMap[catName] ?: 0) + 1
        }

        // Priority distribution
        val prioMap = mutableMapOf<Priority, Int>()
        activeTasks.forEach { task ->
            prioMap[task.priority] = (prioMap[task.priority] ?: 0) + 1
        }

        // Streak calculation
        val completionDates = completedTasks.mapNotNull {
            it.completionTimestamp?.let { ts -> Instant.ofEpochMilli(ts).atZone(zone).toLocalDate() }
        }.toSet()

        var currentStreak = 0
        var checkDate = today
        if (!completionDates.contains(today)) {
            checkDate = today.minusDays(1)
        }
        while (completionDates.contains(checkDate)) {
            currentStreak++
            checkDate = checkDate.minusDays(1)
        }

        // Longest Streak
        val sortedDates = completionDates.sorted()
        var maxStreak = 0
        var tempStreak = 0
        var prevDate: LocalDate? = null
        for (d in sortedDates) {
            if (prevDate == null || d == prevDate.plusDays(1)) {
                tempStreak++
            } else if (d != prevDate) {
                tempStreak = 1
            }
            if (tempStreak > maxStreak) maxStreak = tempStreak
            prevDate = d
        }

        return ProductivityStats(
            createdToday = createdTodayCount,
            completedToday = completedTodayCount,
            completedThisWeek = completedThisWeekCount,
            completedThisMonth = completedThisMonthCount,
            totalActive = totalActive,
            totalCompleted = totalCompleted,
            overdueCount = overdueCount,
            completionRatePercentage = rate,
            currentStreakDays = currentStreak,
            longestStreakDays = maxOf(currentStreak, maxStreak),
            totalFocusMinutesToday = focusMinutesToday,
            totalFocusMinutesThisWeek = focusMinutesThisWeek,
            categoryDistribution = catMap,
            priorityDistribution = prioMap
        )
    }
}
