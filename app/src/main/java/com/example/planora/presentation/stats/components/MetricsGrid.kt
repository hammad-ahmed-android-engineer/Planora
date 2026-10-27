package com.example.planora.presentation.stats.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Timer
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.planora.R
import com.example.planora.domain.model.ProductivityStats
import com.example.planora.core.designsystem.theme.EmeraldPrimary
import com.example.planora.core.designsystem.theme.IndigoPrimary
import com.example.planora.core.designsystem.theme.TealPrimary
import com.example.planora.core.designsystem.theme.VioletPrimary
import com.example.planora.core.designsystem.theme.dimensions

@Composable
fun MetricsGrid(
    stats: ProductivityStats,
    modifier: Modifier = Modifier
) {
    val dims = dimensions
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(dims.spacing.small)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(dims.spacing.small)
        ) {
            AnalyticsBadgeCard(
                label = stringResource(R.string.stats_completed_today),
                value = stats.completedToday.toString(),
                icon = Icons.Default.CheckCircle,
                accentColor = EmeraldPrimary,
                modifier = Modifier.weight(1f)
            )
            AnalyticsBadgeCard(
                label = stringResource(R.string.stats_this_week),
                value = stats.completedThisWeek.toString(),
                icon = Icons.Default.DateRange,
                accentColor = IndigoPrimary,
                modifier = Modifier.weight(1f)
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(dims.spacing.small)
        ) {
            AnalyticsBadgeCard(
                label = stringResource(R.string.stats_focus_today),
                value = "${stats.totalFocusMinutesToday}m",
                icon = Icons.Default.Timer,
                accentColor = TealPrimary,
                modifier = Modifier.weight(1f)
            )
            AnalyticsBadgeCard(
                label = stringResource(R.string.stats_focus_week),
                value = "${stats.totalFocusMinutesThisWeek}m",
                icon = Icons.Default.Schedule,
                accentColor = VioletPrimary,
                modifier = Modifier.weight(1f)
            )
        }
    }
}
