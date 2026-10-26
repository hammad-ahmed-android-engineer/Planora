package com.example.planora.ui.screens.stats.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.planora.R
import com.example.planora.ui.theme.dimensions

@Composable
fun StreakMetricRow(
    currentStreakDays: Int,
    longestStreakDays: Int,
    modifier: Modifier = Modifier
) {
    val dims = dimensions
    Row(
        horizontalArrangement = Arrangement.spacedBy(dims.spacing.small),
        modifier = modifier.fillMaxWidth()
    ) {
        StreakMetricCard(
            title = stringResource(R.string.stats_current_streak),
            days = currentStreakDays,
            isCurrent = true,
            modifier = Modifier.weight(1f)
        )
        StreakMetricCard(
            title = stringResource(R.string.stats_best_record),
            days = longestStreakDays,
            isCurrent = false,
            modifier = Modifier.weight(1f)
        )
    }
}
