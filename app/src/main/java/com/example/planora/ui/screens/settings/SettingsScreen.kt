package com.example.planora.ui.screens.settings

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import com.example.planora.R
import com.example.planora.domain.model.UserSettings
import com.example.planora.ui.components.AppTypography
import com.example.planora.ui.screens.settings.components.AboutPlanoraCard
import com.example.planora.ui.screens.settings.components.AppearanceSettingsCard
import com.example.planora.ui.screens.settings.components.ManagementShortcutsCard
import com.example.planora.ui.screens.settings.components.NotificationSettingsCard
import com.example.planora.ui.theme.dimensions

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    settings: UserSettings,
    onNavigateToCategories: () -> Unit,
    onNavigateToTemplates: () -> Unit,
    onNavigateToStats: () -> Unit,
    onNavigateToArchived: () -> Unit,
    onNavigateToTrash: () -> Unit,
    onThemeModeChanged: (String) -> Unit,
    onAccentColorChanged: (String) -> Unit,
    onToggleNotifications: (Boolean) -> Unit,
    onNotificationStyleChanged: (String) -> Unit
) {
    val dims = dimensions
    val isLandscape = LocalConfiguration.current.orientation == Configuration.ORIENTATION_LANDSCAPE
    val accentColors = listOf("#6366F1", "#8B5CF6", "#10B981", "#06B6D4", "#F59E0B", "#F43F5E")

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    AppTypography.TitleLarge(text = stringResource(R.string.settings_title))
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        }
    ) { paddingValues ->
        if (isLandscape) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(dims.spacing.default)
            ) {
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .verticalScroll(rememberScrollState())
                        .padding(end = dims.spacing.medium)
                ) {
                    AppearanceSettingsCard(
                        themeMode = settings.themeMode,
                        accentColorHex = settings.accentColorHex,
                        accentColors = accentColors,
                        onThemeModeChanged = onThemeModeChanged,
                        onAccentColorChanged = onAccentColorChanged
                    )

                    Spacer(modifier = Modifier.height(dims.spacing.medium))

                    NotificationSettingsCard(
                        notificationsEnabled = settings.notificationsEnabled,
                        notificationStyle = settings.notificationStyle,
                        onToggle = onToggleNotifications,
                        onNotificationStyleChanged = onNotificationStyleChanged
                    )
                }

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .verticalScroll(rememberScrollState())
                        .padding(start = dims.spacing.medium)
                ) {
                    ManagementShortcutsCard(
                        onNavigateToCategories = onNavigateToCategories,
                        onNavigateToTemplates = onNavigateToTemplates,
                        onNavigateToStats = onNavigateToStats,
                        onNavigateToArchived = onNavigateToArchived,
                        onNavigateToTrash = onNavigateToTrash
                    )

                    Spacer(modifier = Modifier.height(dims.spacing.medium))

                    AboutPlanoraCard()
                }
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = dims.spacing.default, vertical = dims.spacing.small)
            ) {
                AppearanceSettingsCard(
                    themeMode = settings.themeMode,
                    accentColorHex = settings.accentColorHex,
                    accentColors = accentColors,
                    onThemeModeChanged = onThemeModeChanged,
                    onAccentColorChanged = onAccentColorChanged
                )

                Spacer(modifier = Modifier.height(dims.spacing.medium))

                NotificationSettingsCard(
                    notificationsEnabled = settings.notificationsEnabled,
                    notificationStyle = settings.notificationStyle,
                    onToggle = onToggleNotifications,
                    onNotificationStyleChanged = onNotificationStyleChanged
                )

                Spacer(modifier = Modifier.height(dims.spacing.medium))

                ManagementShortcutsCard(
                    onNavigateToCategories = onNavigateToCategories,
                    onNavigateToTemplates = onNavigateToTemplates,
                    onNavigateToStats = onNavigateToStats,
                    onNavigateToArchived = onNavigateToArchived,
                    onNavigateToTrash = onNavigateToTrash
                )

                Spacer(modifier = Modifier.height(dims.spacing.medium))

                AboutPlanoraCard()

                Spacer(modifier = Modifier.height(dims.spacing.large))
            }
        }
    }
}
