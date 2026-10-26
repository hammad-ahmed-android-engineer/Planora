package com.example.planora.ui.screens.taskdetail.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Repeat
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.example.planora.R
import com.example.planora.domain.model.RecurrenceType
import com.example.planora.domain.model.Task
import com.example.planora.ui.components.AppTypography
import com.example.planora.ui.theme.IndigoPrimary
import com.example.planora.ui.theme.dimensions
import com.example.planora.util.formatIsoDateToDisplay
import com.example.planora.util.formatIsoTimeToDisplay
import com.example.planora.util.formatRecurrenceLabel

@Composable
fun TaskScheduleCard(
    task: Task,
    modifier: Modifier = Modifier
) {
    val dims = dimensions
    val shape = RoundedCornerShape(dims.corners.extraLarge)

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
            AppTypography.TitleMedium(
                text = stringResource(R.string.task_editor_schedule),
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(dims.spacing.medium))

            task.dueDate?.let { dueDate ->
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.CalendarMonth,
                        contentDescription = null,
                        tint = IndigoPrimary,
                        modifier = Modifier.size(dims.icons.medium)
                    )
                    Spacer(modifier = Modifier.width(dims.spacing.small))
                    AppTypography.BodyMedium(
                        text = stringResource(R.string.task_detail_due, dueDate.formatIsoDateToDisplay()),
                        fontWeight = FontWeight.SemiBold
                    )
                }
                Spacer(modifier = Modifier.height(dims.spacing.small))
            }

            task.dueTime?.let { dueTime ->
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Schedule,
                        contentDescription = null,
                        tint = IndigoPrimary,
                        modifier = Modifier.size(dims.icons.medium)
                    )
                    Spacer(modifier = Modifier.width(dims.spacing.small))
                    AppTypography.BodyMedium(
                        text = stringResource(R.string.task_detail_time, dueTime.formatIsoTimeToDisplay()),
                        fontWeight = FontWeight.SemiBold
                    )
                }
                Spacer(modifier = Modifier.height(dims.spacing.small))
            }

            if (task.recurrenceType != RecurrenceType.NONE) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Repeat,
                        contentDescription = null,
                        tint = IndigoPrimary,
                        modifier = Modifier.size(dims.icons.medium)
                    )
                    Spacer(modifier = Modifier.width(dims.spacing.small))
                    AppTypography.BodyMedium(
                        text = task.recurrenceType.formatRecurrenceLabel(task.recurrenceInterval),
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
    }
}
