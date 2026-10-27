package com.example.planora.presentation.taskeditor.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Repeat
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.example.planora.R
import com.example.planora.core.designsystem.components.AppCard
import com.example.planora.core.designsystem.theme.AppTypography
import com.example.planora.core.designsystem.theme.dimensions
import com.example.planora.core.utils.clickableDebounced
import com.example.planora.core.utils.formatIsoDateToDisplay
import com.example.planora.core.utils.formatIsoTimeToDisplay
import com.example.planora.core.utils.formatRecurrenceLabel
import com.example.planora.domain.model.RecurrenceType

@Composable
fun ScheduleSectionCard(
    dueDate: String?,
    dueTime: String?,
    recurrenceType: RecurrenceType,
    recurrenceInterval: Int,
    onOpenDatePicker: () -> Unit,
    onOpenTimePicker: () -> Unit,
    onOpenRecurrenceMenu: () -> Unit,
    modifier: Modifier = Modifier
) {
    val dims = dimensions
    val primaryColor = MaterialTheme.colorScheme.primary

    AppCard(
        modifier = modifier.fillMaxWidth(),
        containerColor = MaterialTheme.colorScheme.surface,
        borderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.25f)
    ) {
        Column(modifier = Modifier.padding(dims.spacing.default)) {
            AppTypography.TitleMedium(
                text = stringResource(R.string.task_editor_schedule),
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(dims.spacing.medium))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(dims.spacing.small)
            ) {
                AppCard(
                    modifier = Modifier
                        .weight(1f)
                        .clickableDebounced(onClick = onOpenDatePicker),
                    containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                    borderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(dims.spacing.medium)
                    ) {
                        Icon(imageVector = Icons.Default.CalendarToday, contentDescription = null, tint = primaryColor, modifier = Modifier.size(dims.icons.medium))
                        Spacer(modifier = Modifier.width(dims.spacing.small))
                        Column {
                            AppTypography.Caption(text = stringResource(R.string.task_editor_due_date))
                            AppTypography.BodyMedium(
                                text = dueDate?.formatIsoDateToDisplay() ?: stringResource(R.string.task_editor_none),
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                }

                AppCard(
                    modifier = Modifier
                        .weight(1f)
                        .clickableDebounced(onClick = onOpenTimePicker),
                    containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                    borderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(dims.spacing.medium)
                    ) {
                        Icon(imageVector = Icons.Default.Schedule, contentDescription = null, tint = primaryColor, modifier = Modifier.size(dims.icons.medium))
                        Spacer(modifier = Modifier.width(dims.spacing.small))
                        Column {
                            AppTypography.Caption(text = stringResource(R.string.task_editor_due_time))
                            AppTypography.BodyMedium(
                                text = dueTime?.formatIsoTimeToDisplay()?.ifEmpty { null } ?: stringResource(R.string.task_editor_none),
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(dims.spacing.small))

            AppCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickableDebounced(onClick = onOpenRecurrenceMenu),
                containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                borderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(dims.spacing.medium)
                ) {
                    Icon(imageVector = Icons.Default.Repeat, contentDescription = null, tint = primaryColor, modifier = Modifier.size(dims.icons.medium))
                    Spacer(modifier = Modifier.width(dims.spacing.small))
                    Column {
                        AppTypography.Caption(text = stringResource(R.string.task_editor_repeat))
                        AppTypography.BodyMedium(
                            text = recurrenceType.formatRecurrenceLabel(recurrenceInterval),
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }
        }
    }
}
