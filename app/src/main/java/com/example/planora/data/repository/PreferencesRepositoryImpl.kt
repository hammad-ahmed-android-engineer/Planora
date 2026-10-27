package com.example.planora.data.repository

import com.example.planora.data.datastore.UserPreferencesDataStore
import com.example.planora.domain.model.UserPreferences
import com.example.planora.domain.repository.PreferencesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PreferencesRepositoryImpl @Inject constructor(
    private val dataStore: UserPreferencesDataStore
) : PreferencesRepository {

    override val userPreferencesFlow: Flow<UserPreferences> = dataStore.userPreferencesFlow

    override suspend fun updateThemeMode(mode: String) {
        dataStore.updateThemeMode(mode)
    }

    override suspend fun updateAccentColor(colorHex: String) {
        dataStore.updateAccentColor(colorHex)
    }

    override suspend fun updateCompactDensity(enabled: Boolean) {
        dataStore.updateCompactDensity(enabled)
    }

    override suspend fun updateShowCompletedTasks(show: Boolean) {
        dataStore.updateShowCompletedTasks(show)
    }

    override suspend fun updateNotificationsEnabled(enabled: Boolean) {
        dataStore.updateNotificationsEnabled(enabled)
    }

    override suspend fun updateNotificationStyle(style: String) {
        dataStore.updateNotificationStyle(style)
    }

    override suspend fun updateNotificationStylePrompted(prompted: Boolean) {
        dataStore.updateNotificationStylePrompted(prompted)
    }

    override suspend fun updateFocusSettings(focusMin: Int, shortBreakMin: Int, longBreakMin: Int, sessions: Int) {
        dataStore.updateFocusSettings(focusMin, shortBreakMin, longBreakMin, sessions)
    }

    override suspend fun updateStreak(currentStreak: Int, longestStreak: Int, activeDate: String) {
        dataStore.updateStreak(currentStreak, longestStreak, activeDate)
    }

    override suspend fun setOnboardingCompleted(completed: Boolean) {
        dataStore.setOnboardingCompleted(completed)
    }

    override suspend fun clearAllPreferences() {
        dataStore.clearAllPreferences()
    }
}
