package com.example.planora.domain.model

/**
 * Domain model representing user-configurable application preferences.
 */
data class UserSettings(
    val themeMode: String = "SYSTEM", // LIGHT, DARK, SYSTEM
    val accentColorHex: String = "#6366F1",
    val compactDensity: Boolean = false,
    val showCompletedTasks: Boolean = true,
    val showDescriptionsInList: Boolean = true,
    val defaultPriority: String = "NONE",
    val defaultCategoryId: String? = null,
    val defaultDurationMinutes: Int = 30,
    val firstDayOfWeek: Int = 2, // 1 = Sunday, 2 = Monday
    val autoCompleteParentWithSubtasks: Boolean = true,
    val notificationsEnabled: Boolean = true,
    val quietHoursEnabled: Boolean = false,
    val quietHoursStart: String = "22:00",
    val quietHoursEnd: String = "07:00",
    val focusDurationMinutes: Int = 25,
    val shortBreakMinutes: Int = 5,
    val longBreakMinutes: Int = 15,
    val sessionsBeforeLongBreak: Int = 4,
    val currentStreak: Int = 0,
    val longestStreak: Int = 0,
    val lastActiveDate: String? = null,
    val onboardingCompleted: Boolean = false,
    val notificationStyle: String = "NOTIFICATION", // NOTIFICATION, FULL_SCREEN
    val notificationStylePrompted: Boolean = false
)
