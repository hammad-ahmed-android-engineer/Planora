package com.example.planora.domain.usecase

import com.example.planora.domain.model.UserPreferences
import com.example.planora.domain.repository.PreferencesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * UseCase to handle accessing and mutating user application preferences.
 */
class ManagePreferencesUseCase @Inject constructor(
    private val preferencesRepository: PreferencesRepository
) {
    val userPreferencesFlow: Flow<UserPreferences> = preferencesRepository.userPreferencesFlow

    suspend fun updateNotificationStyle(style: String) {
        preferencesRepository.updateNotificationStyle(style)
    }

    suspend fun updateNotificationStylePrompted(prompted: Boolean) {
        preferencesRepository.updateNotificationStylePrompted(prompted)
    }

    suspend fun updateThemeMode(mode: String) {
        preferencesRepository.updateThemeMode(mode)
    }

    suspend fun updateAccentColor(colorHex: String) {
        preferencesRepository.updateAccentColor(colorHex)
    }

    suspend fun updateCompactDensity(enabled: Boolean) {
        preferencesRepository.updateCompactDensity(enabled)
    }

    suspend fun updateShowCompletedTasks(show: Boolean) {
        preferencesRepository.updateShowCompletedTasks(show)
    }

    suspend fun updateNotificationsEnabled(enabled: Boolean) {
        preferencesRepository.updateNotificationsEnabled(enabled)
    }

    suspend fun updateFocusSettings(focusMin: Int, shortBreakMin: Int, longBreakMin: Int, sessions: Int) {
        preferencesRepository.updateFocusSettings(focusMin, shortBreakMin, longBreakMin, sessions)
    }

    suspend fun clearAllPreferences() {
        preferencesRepository.clearAllPreferences()
    }
}
