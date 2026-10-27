package com.example.planora.presentation.templates.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.example.planora.R
import com.example.planora.domain.model.Category
import com.example.planora.domain.model.Priority
import com.example.planora.domain.model.TaskTemplate
import com.example.planora.core.designsystem.theme.AppTypography
import com.example.planora.core.designsystem.components.CategoryChip
import com.example.planora.core.designsystem.components.PriorityBadge
import com.example.planora.core.designsystem.theme.IndigoPrimary
import com.example.planora.core.designsystem.theme.dimensions
import com.example.planora.core.utils.rememberDebouncedClick

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun TemplateCardItem(
    template: TaskTemplate,
    category: Category?,
    onUseTemplate: (TaskTemplate) -> Unit,
    onEditTemplate: (TaskTemplate) -> Unit,
    onDeleteTemplate: (TaskTemplate) -> Unit,
    modifier: Modifier = Modifier
) {
    val dims = dimensions
    val shape = RoundedCornerShape(dims.corners.extraLarge)
    val debouncedEdit = rememberDebouncedClick { onEditTemplate(template) }
    val debouncedDelete = rememberDebouncedClick { onDeleteTemplate(template) }
    val debouncedUse = rememberDebouncedClick { onUseTemplate(template) }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = dims.spacing.tiny)
            .clip(shape)
            .border(dims.borders.thin, MaterialTheme.colorScheme.outline.copy(alpha = 0.25f), shape),
        shape = shape,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = dims.elevations.low)
    ) {
        Column(modifier = Modifier.padding(dims.spacing.default)) {
            Row(
                verticalAlignment = Alignment.Top,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    AppTypography.TitleMedium(
                        text = template.title,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    if (template.description.isNotBlank()) {
                        Spacer(modifier = Modifier.height(dims.spacing.tiny))
                        AppTypography.BodySmall(
                            text = template.description,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                Row(horizontalArrangement = Arrangement.End) {
                    IconButton(
                        onClick = debouncedEdit,
                        modifier = Modifier.size(dims.components.buttonSmallHeight)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = stringResource(R.string.action_edit),
                            tint = IndigoPrimary,
                            modifier = Modifier.size(dims.icons.small)
                        )
                    }

                    IconButton(
                        onClick = debouncedDelete,
                        modifier = Modifier.size(dims.components.buttonSmallHeight)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = stringResource(R.string.action_delete),
                            tint = MaterialTheme.colorScheme.error,
                            modifier = Modifier.size(dims.icons.small)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(dims.spacing.small))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(dims.spacing.tiny),
                    verticalArrangement = Arrangement.spacedBy(dims.spacing.tiny),
                    modifier = Modifier.weight(1f, fill = false)
                ) {
                    category?.let { CategoryChip(category = it) }

                    if (template.priority != Priority.NONE) {
                        PriorityBadge(priority = template.priority)
                    }

                    val dueDaysLabel = if (template.relativeDueDays == 0) {
                        stringResource(R.string.templates_due_today)
                    } else {
                        stringResource(R.string.templates_due_in_days, template.relativeDueDays)
                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .clip(RoundedCornerShape(dims.corners.small))
                            .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f))
                            .padding(horizontal = dims.spacing.small, vertical = dims.spacing.micro)
                    ) {
                        Icon(
                            imageVector = Icons.Default.CalendarMonth,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.size(dims.icons.micro)
                        )
                        Spacer(modifier = Modifier.width(dims.spacing.tiny))
                        AppTypography.Caption(
                            text = dueDaysLabel,
                            fontWeight = FontWeight.Medium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                Spacer(modifier = Modifier.width(dims.spacing.small))

                Button(
                    onClick = debouncedUse,
                    shape = RoundedCornerShape(dims.corners.medium),
                    contentPadding = PaddingValues(horizontal = dims.spacing.medium, vertical = dims.spacing.tiny),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = IndigoPrimary,
                        contentColor = Color.White
                    )
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = null,
                        modifier = Modifier.size(dims.icons.small)
                    )
                    Spacer(modifier = Modifier.width(dims.spacing.tiny))
                    AppTypography.BodySmall(
                        text = stringResource(R.string.templates_use_button),
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        maxLines = 1
                    )
                }
            }

            if (template.defaultSubtasks.isNotEmpty()) {
                Spacer(modifier = Modifier.height(dims.spacing.small))
                AppTypography.Caption(
                    text = "Subtasks Checklist (${template.defaultSubtasks.size}):",
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(dims.spacing.tiny))
                Column(verticalArrangement = Arrangement.spacedBy(dims.spacing.micro)) {
                    template.defaultSubtasks.take(3).forEach { subTitle ->
                        AppTypography.BodySmall(
                            text = "• $subTitle",
                            color = MaterialTheme.colorScheme.onSurface,
                            maxLines = 1
                        )
                    }
                    if (template.defaultSubtasks.size > 3) {
                        AppTypography.Caption(
                            text = "+ ${template.defaultSubtasks.size - 3} more subtasks",
                            color = IndigoPrimary
                        )
                    }
                }
            }
        }
    }
}
