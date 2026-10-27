package com.example.planora.domain.repository

import com.example.planora.domain.model.UserPreferences
import kotlinx.coroutines.flow.Flow

/**
 * Domain repository interface for managing persistent user preferences and app configuration.
 */
interface PreferencesRepository {
    val userPreferencesFlow: Flow<UserPreferences>

    suspend fun updateThemeMode(mode: String)
    suspend fun updateAccentColor(colorHex: String)
    suspend fun updateCompactDensity(enabled: Boolean)
    suspend fun updateShowCompletedTasks(show: Boolean)
    suspend fun updateNotificationsEnabled(enabled: Boolean)
    suspend fun updateNotificationStyle(style: String)
    suspend fun updateNotificationStylePrompted(prompted: Boolean)
    suspend fun updateFocusSettings(focusMin: Int, shortBreakMin: Int, longBreakMin: Int, sessions: Int)
    suspend fun updateStreak(currentStreak: Int, longestStreak: Int, activeDate: String)
    suspend fun setOnboardingCompleted(completed: Boolean)
    suspend fun clearAllPreferences()
}
