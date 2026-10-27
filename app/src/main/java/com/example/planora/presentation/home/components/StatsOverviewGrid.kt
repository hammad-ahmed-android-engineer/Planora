package com.example.planora.presentation.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.planora.R
import com.example.planora.core.designsystem.theme.EmeraldPrimary
import com.example.planora.core.designsystem.theme.PriorityUrgent
import com.example.planora.core.designsystem.theme.dimensions

@Composable
fun StatsOverviewGrid(
    todayCount: Int,
    completedCount: Int,
    overdueCount: Int
) {
    val dims = dimensions
    val primaryColor = MaterialTheme.colorScheme.primary
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(dims.spacing.small)
    ) {
        StatPillCard(stringResource(R.string.home_stat_today), todayCount.toString(), Icons.Default.Schedule, primaryColor, Modifier.weight(1f))
        StatPillCard(stringResource(R.string.home_stat_done), completedCount.toString(), Icons.Default.CheckCircle, EmeraldPrimary, Modifier.weight(1f))
        StatPillCard(stringResource(R.string.home_stat_overdue), overdueCount.toString(), Icons.Default.Warning, PriorityUrgent, Modifier.weight(1f))
    }
}
