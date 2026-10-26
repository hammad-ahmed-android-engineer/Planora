package com.example.planora.ui.screens.home.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.example.planora.R
import com.example.planora.domain.model.Task
import com.example.planora.ui.components.AppTypography
import com.example.planora.ui.theme.EmeraldPrimary
import com.example.planora.ui.theme.dimensions

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

    val animatedProgress by animateFloatAsState(
        targetValue = progressFraction,
        animationSpec = spring(stiffness = 200f),
        label = "heroProgress"
    )

    val shape = RoundedCornerShape(dims.corners.extraLarge)

    Card(
        modifier = modifier
            .fillMaxWidth()
            .clip(shape)
            .border(
                dims.borders.thin,
                primaryColor.copy(alpha = 0.25f),
                shape
            ),
        shape = shape,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f)),
        elevation = CardDefaults.cardElevation(defaultElevation = dims.elevations.none)
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
                    text = "${(animatedProgress * 100).toInt()}%",
                    fontWeight = FontWeight.Black,
                    color = if (completedToday == totalToday && totalToday > 0) EmeraldPrimary else primaryColor
                )
            }

            Spacer(modifier = Modifier.height(dims.spacing.medium))

            LinearProgressIndicator(
                progress = { animatedProgress },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(dims.spacing.small)
                    .clip(RoundedCornerShape(dims.corners.micro)),
                color = if (completedToday == totalToday && totalToday > 0) EmeraldPrimary else primaryColor,
                trackColor = MaterialTheme.colorScheme.surface
            )
        }
    }
}
