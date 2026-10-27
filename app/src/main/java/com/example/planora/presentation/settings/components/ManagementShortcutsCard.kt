package com.example.planora.presentation.settings.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.FormatListBulleted
import androidx.compose.material.icons.filled.Archive
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material.icons.filled.Insights
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.example.planora.R
import com.example.planora.core.designsystem.components.AppCard
import com.example.planora.core.designsystem.theme.IndigoPrimary
import com.example.planora.core.designsystem.theme.dimensions

@Composable
fun ManagementShortcutsCard(
    onNavigateToCategories: () -> Unit,
    onNavigateToTemplates: () -> Unit,
    onNavigateToStats: () -> Unit,
    onNavigateToArchived: () -> Unit,
    onNavigateToTrash: () -> Unit,
    modifier: Modifier = Modifier
) {
    val dims = dimensions

    AppCard(
        modifier = modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(horizontal = dims.spacing.medium, vertical = dims.spacing.small)) {
            ShortcutRowItem(
                title = stringResource(R.string.settings_categories_title),
                subtitle = stringResource(R.string.settings_categories_subtitle),
                icon = Icons.Default.Folder,
                iconColor = IndigoPrimary,
                onClick = onNavigateToCategories
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(dims.borders.thin)
                    .background(MaterialTheme.colorScheme.outline.copy(alpha = 0.15f))
            )

            ShortcutRowItem(
                title = stringResource(R.string.settings_templates_title),
                subtitle = stringResource(R.string.settings_templates_subtitle),
                icon = Icons.AutoMirrored.Filled.FormatListBulleted,
                iconColor = Color(0xFF10B981),
                onClick = onNavigateToTemplates
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(dims.borders.thin)
                    .background(MaterialTheme.colorScheme.outline.copy(alpha = 0.15f))
            )

            ShortcutRowItem(
                title = stringResource(R.string.settings_analytics_title),
                subtitle = stringResource(R.string.settings_analytics_subtitle),
                icon = Icons.Default.Insights,
                iconColor = Color(0xFFF59E0B),
                onClick = onNavigateToStats
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(dims.borders.thin)
                    .background(MaterialTheme.colorScheme.outline.copy(alpha = 0.15f))
            )

            ShortcutRowItem(
                title = stringResource(R.string.settings_archived_title),
                subtitle = stringResource(R.string.settings_archived_subtitle),
                icon = Icons.Default.Archive,
                iconColor = Color(0xFF8B5CF6),
                onClick = onNavigateToArchived
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(dims.borders.thin)
                    .background(MaterialTheme.colorScheme.outline.copy(alpha = 0.15f))
            )

            ShortcutRowItem(
                title = stringResource(R.string.settings_trash_title),
                subtitle = stringResource(R.string.settings_trash_subtitle),
                icon = Icons.Default.Delete,
                iconColor = Color(0xFFEF4444),
                onClick = onNavigateToTrash
            )
        }
    }
}
