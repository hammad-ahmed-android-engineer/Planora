package com.example.planora.presentation.settings

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun SettingsRoute(
    onNavigateToCategories: () -> Unit,
    onNavigateToTemplates: () -> Unit,
    onNavigateToStats: () -> Unit,
    onNavigateToArchived: () -> Unit,
    onNavigateToTrash: () -> Unit,
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val userPreferences by viewModel.userPreferences.collectAsStateWithLifecycle()

    SettingsScreen(
        userPreferences = userPreferences,
        onNavigateToCategories = onNavigateToCategories,
        onNavigateToTemplates = onNavigateToTemplates,
        onNavigateToStats = onNavigateToStats,
        onNavigateToArchived = onNavigateToArchived,
        onNavigateToTrash = onNavigateToTrash,
        onThemeModeChanged = viewModel::updateThemeMode,
        onAccentColorChanged = viewModel::updateAccentColor,
        onToggleNotifications = viewModel::updateNotificationsEnabled,
        onNotificationStyleChanged = viewModel::updateNotificationStyle
    )
}
