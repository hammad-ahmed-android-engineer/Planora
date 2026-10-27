package com.example.planora.presentation.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.example.planora.R
import com.example.planora.core.designsystem.components.AppCard
import com.example.planora.core.designsystem.components.AppLinearProgressIndicator
import com.example.planora.core.designsystem.theme.AppTypography
import com.example.planora.core.designsystem.theme.EmeraldPrimary
import com.example.planora.core.designsystem.theme.dimensions
import com.example.planora.domain.model.Task

@Composable
fun DailyProgressHeroCard(
    todayTasks: List<Task>,
    activeCount: Int,
    completedCount: Int,
    modifier: Modifier = Modifier
) {
    val dims = dimensions
    val primaryColor = MaterialTheme.colorScheme.primary
    val totalToday = todayTasks.size
    val completedToday = todayTasks.count { it.isCompleted }
    val progressFraction = if (totalToday > 0) completedToday.toFloat() / totalToday.toFloat() else 0f

    AppCard(
        modifier = modifier.fillMaxWidth(),
        containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f),
        borderColor = primaryColor.copy(alpha = 0.25f)
    ) {
        Column(modifier = Modifier.padding(dims.spacing.default)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column {
                    AppTypography.TitleMedium(
                        text = stringResource(R.string.home_todays_focus),
                        fontWeight = FontWeight.Bold
                    )
                    AppTypography.BodySmall(
                        text = if (totalToday == 0) {
                            stringResource(R.string.home_progress_all_clear)
                        } else if (completedToday == totalToday) {
                            stringResource(R.string.home_progress_awesome)
                        } else {
                            stringResource(R.string.home_progress_status, completedToday, totalToday)
                        },
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                AppTypography.TitleLarge(
                    text = "${(progressFraction * 100).toInt()}%",
                    fontWeight = FontWeight.Black,
                    color = if (completedToday == totalToday && totalToday > 0) EmeraldPrimary else primaryColor
                )
            }

            Spacer(modifier = Modifier.height(dims.spacing.medium))

            AppLinearProgressIndicator(
                progress = progressFraction,
                color = if (completedToday == totalToday && totalToday > 0) EmeraldPrimary else primaryColor,
                trackColor = MaterialTheme.colorScheme.surface,
                height = dims.spacing.small
            )
        }
    }
}
