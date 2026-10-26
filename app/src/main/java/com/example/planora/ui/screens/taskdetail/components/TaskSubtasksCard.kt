package com.example.planora.ui.screens.taskdetail.components

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
import androidx.compose.foundation.layout.width
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
import com.example.planora.domain.model.Subtask
import com.example.planora.domain.model.Task
import com.example.planora.ui.components.AnimatedTaskCheckbox
import com.example.planora.ui.components.AppTypography
import com.example.planora.ui.theme.EmeraldPrimary
import com.example.planora.ui.theme.IndigoPrimary
import com.example.planora.ui.theme.dimensions

@Composable
fun TaskSubtasksCard(
    task: Task,
    onToggleSubtask: (Subtask) -> Unit,
    modifier: Modifier = Modifier
) {
    val dims = dimensions
    val shape = RoundedCornerShape(dims.corners.extraLarge)
    val completedCount = task.subtasks.count { it.isCompleted }
    val progress = if (task.subtasks.isNotEmpty()) completedCount.toFloat() / task.subtasks.size.toFloat() else 0f

    val animatedProgress by animateFloatAsState(
        targetValue = progress,
        animationSpec = spring(stiffness = 200f),
        label = "subtaskProgressDetail"
    )

    Card(
        modifier = modifier
            .fillMaxWidth()
            .clip(shape)
            .border(dims.borders.thin, MaterialTheme.colorScheme.outline.copy(alpha = 0.25f), shape),
        shape = shape,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = dims.elevations.low)
    ) {
        Column(modifier = Modifier.padding(dims.spacing.default)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                AppTypography.TitleMedium(
                    text = stringResource(R.string.task_detail_subtasks),
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                AppTypography.Caption(
                    text = stringResource(R.string.task_detail_subtasks_progress, completedCount, task.subtasks.size),
                    fontWeight = FontWeight.Bold,
                    color = IndigoPrimary
                )
            }

            Spacer(modifier = Modifier.height(dims.spacing.small))

            LinearProgressIndicator(
                progress = { animatedProgress },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(dims.spacing.tiny)
                    .clip(RoundedCornerShape(dims.corners.micro)),
                color = EmeraldPrimary,
                trackColor = MaterialTheme.colorScheme.surfaceVariant
            )

            Spacer(modifier = Modifier.height(dims.spacing.medium))

            task.subtasks.forEach { subtask ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = dims.spacing.tiny)
                ) {
                    AnimatedTaskCheckbox(
                        checked = subtask.isCompleted,
                        onCheckedChange = { onToggleSubtask(subtask) }
                    )
                    Spacer(modifier = Modifier.width(dims.spacing.medium))
                    AppTypography.BodyMedium(
                        text = subtask.title,
                        color = if (subtask.isCompleted) MaterialTheme.colorScheme.onSurfaceVariant else MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }
    }
}
