package com.example.planora.data.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.planora.domain.model.UserPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserPreferencesDataStore @Inject constructor(
    private val dataStore: DataStore<Preferences>,
    @ApplicationContext private val context: Context
) {

    private object Keys {
        val THEME_MODE = stringPreferencesKey("theme_mode")
        val ACCENT_COLOR_HEX = stringPreferencesKey("accent_color_hex")
        val COMPACT_DENSITY = booleanPreferencesKey("compact_density")
        val SHOW_COMPLETED_TASKS = booleanPreferencesKey("show_completed_tasks")
        val SHOW_DESCRIPTIONS_IN_LIST = booleanPreferencesKey("show_descriptions_in_list")
        val DEFAULT_PRIORITY = stringPreferencesKey("default_priority")
        val DEFAULT_CATEGORY_ID = stringPreferencesKey("default_category_id")
        val DEFAULT_DURATION_MINUTES = intPreferencesKey("default_duration_minutes")
        val FIRST_DAY_OF_WEEK = intPreferencesKey("first_day_of_week")
        val AUTO_COMPLETE_PARENT = booleanPreferencesKey("auto_complete_parent")
        val NOTIFICATIONS_ENABLED = booleanPreferencesKey("notifications_enabled")
        val QUIET_HOURS_ENABLED = booleanPreferencesKey("quiet_hours_enabled")
        val QUIET_HOURS_START = stringPreferencesKey("quiet_hours_start")
        val QUIET_HOURS_END = stringPreferencesKey("quiet_hours_end")
        val FOCUS_DURATION_MINUTES = intPreferencesKey("focus_duration_minutes")
        val SHORT_BREAK_MINUTES = intPreferencesKey("short_break_minutes")
        val LONG_BREAK_MINUTES = intPreferencesKey("long_break_minutes")
        val SESSIONS_BEFORE_LONG_BREAK = intPreferencesKey("sessions_before_long_break")
        val CURRENT_STREAK = intPreferencesKey("current_streak")
        val LONGEST_STREAK = intPreferencesKey("longest_streak")
        val LAST_ACTIVE_DATE = stringPreferencesKey("last_active_date")
        val ONBOARDING_COMPLETED = booleanPreferencesKey("onboarding_completed")
        val NOTIFICATION_STYLE = stringPreferencesKey("notification_style")
        val NOTIFICATION_STYLE_PROMPTED = booleanPreferencesKey("notification_style_prompted")
    }

    val userPreferencesFlow: Flow<UserPreferences> = dataStore.data.map { prefs ->
        UserPreferences(
            themeMode = prefs[Keys.THEME_MODE] ?: "SYSTEM",
            accentColorHex = prefs[Keys.ACCENT_COLOR_HEX] ?: "#6366F1",
            compactDensity = prefs[Keys.COMPACT_DENSITY] ?: false,
            showCompletedTasks = prefs[Keys.SHOW_COMPLETED_TASKS] ?: true,
            showDescriptionsInList = prefs[Keys.SHOW_DESCRIPTIONS_IN_LIST] ?: true,
            defaultPriority = prefs[Keys.DEFAULT_PRIORITY] ?: "NONE",
            defaultCategoryId = prefs[Keys.DEFAULT_CATEGORY_ID],
            defaultDurationMinutes = prefs[Keys.DEFAULT_DURATION_MINUTES] ?: 30,
            firstDayOfWeek = prefs[Keys.FIRST_DAY_OF_WEEK] ?: 2,
            autoCompleteParentWithSubtasks = prefs[Keys.AUTO_COMPLETE_PARENT] ?: true,
            notificationsEnabled = prefs[Keys.NOTIFICATIONS_ENABLED] ?: true,
            quietHoursEnabled = prefs[Keys.QUIET_HOURS_ENABLED] ?: false,
            quietHoursStart = prefs[Keys.QUIET_HOURS_START] ?: "22:00",
            quietHoursEnd = prefs[Keys.QUIET_HOURS_END] ?: "07:00",
            focusDurationMinutes = prefs[Keys.FOCUS_DURATION_MINUTES] ?: 25,
            shortBreakMinutes = prefs[Keys.SHORT_BREAK_MINUTES] ?: 5,
            longBreakMinutes = prefs[Keys.LONG_BREAK_MINUTES] ?: 15,
            sessionsBeforeLongBreak = prefs[Keys.SESSIONS_BEFORE_LONG_BREAK] ?: 4,
            currentStreak = prefs[Keys.CURRENT_STREAK] ?: 0,
            longestStreak = prefs[Keys.LONGEST_STREAK] ?: 0,
            lastActiveDate = prefs[Keys.LAST_ACTIVE_DATE],
            onboardingCompleted = prefs[Keys.ONBOARDING_COMPLETED] ?: false,
            notificationStyle = prefs[Keys.NOTIFICATION_STYLE] ?: "NOTIFICATION",
            notificationStylePrompted = prefs[Keys.NOTIFICATION_STYLE_PROMPTED] ?: false
        )
    }

    suspend fun updateThemeMode(mode: String) {
        dataStore.edit { prefs -> prefs[Keys.THEME_MODE] = mode }
    }

    suspend fun updateAccentColor(colorHex: String) {
        dataStore.edit { prefs -> prefs[Keys.ACCENT_COLOR_HEX] = colorHex }
    }

    suspend fun updateCompactDensity(enabled: Boolean) {
        dataStore.edit { prefs -> prefs[Keys.COMPACT_DENSITY] = enabled }
    }

    suspend fun updateShowCompletedTasks(show: Boolean) {
        dataStore.edit { prefs -> prefs[Keys.SHOW_COMPLETED_TASKS] = show }
    }

    suspend fun updateNotificationsEnabled(enabled: Boolean) {
        dataStore.edit { prefs -> prefs[Keys.NOTIFICATIONS_ENABLED] = enabled }
    }

    suspend fun updateNotificationStyle(style: String) {
        try {
            context.getSharedPreferences("planora_direct_prefs", Context.MODE_PRIVATE)
                .edit()
                .putString("notification_style", style)
                .apply()
        } catch (_: Exception) {}
        dataStore.edit { prefs -> prefs[Keys.NOTIFICATION_STYLE] = style }
    }

    suspend fun updateNotificationStylePrompted(prompted: Boolean) {
        dataStore.edit { prefs -> prefs[Keys.NOTIFICATION_STYLE_PROMPTED] = prompted }
    }

    suspend fun updateFocusSettings(focusMin: Int, shortBreakMin: Int, longBreakMin: Int, sessions: Int) {
        dataStore.edit { prefs ->
            prefs[Keys.FOCUS_DURATION_MINUTES] = focusMin
            prefs[Keys.SHORT_BREAK_MINUTES] = shortBreakMin
            prefs[Keys.LONG_BREAK_MINUTES] = longBreakMin
            prefs[Keys.SESSIONS_BEFORE_LONG_BREAK] = sessions
        }
    }

    suspend fun updateStreak(currentStreak: Int, longestStreak: Int, activeDate: String) {
        dataStore.edit { prefs ->
            prefs[Keys.CURRENT_STREAK] = currentStreak
            prefs[Keys.LONGEST_STREAK] = longestStreak
            prefs[Keys.LAST_ACTIVE_DATE] = activeDate
        }
    }

    suspend fun setOnboardingCompleted(completed: Boolean) {
        dataStore.edit { prefs -> prefs[Keys.ONBOARDING_COMPLETED] = completed }
    }

    suspend fun clearAllPreferences() {
        dataStore.edit { prefs -> prefs.clear() }
    }
}
