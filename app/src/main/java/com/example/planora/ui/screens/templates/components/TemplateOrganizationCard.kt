package com.example.planora.ui.screens.templates.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
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
import com.example.planora.ui.components.AppTypography
import com.example.planora.ui.theme.dimensions
import com.example.planora.util.color
import com.example.planora.util.clickableDebounced

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun TemplateOrganizationCard(
    selectedCategoryId: String?,
    categoriesList: List<Category>,
    selectedPriority: Priority,
    relativeDueDays: Int,
    showCategoryMenu: Boolean,
    onToggleCategoryMenu: (Boolean) -> Unit,
    onSelectCategory: (String?) -> Unit,
    onSelectPriority: (Priority) -> Unit,
    onSelectRelativeDays: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val dims = dimensions
    val primaryColor = MaterialTheme.colorScheme.primary
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
                text = stringResource(R.string.task_editor_organization),
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(dims.spacing.medium))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(dims.spacing.small)
            ) {
                val selectedCategoryName = categoriesList.find { it.id == selectedCategoryId }?.name ?: stringResource(R.string.filter_all_tasks)

                Box(modifier = Modifier.weight(1f)) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(dims.corners.medium))
                            .border(dims.borders.thin, MaterialTheme.colorScheme.outline.copy(alpha = 0.3f), RoundedCornerShape(dims.corners.medium))
                            .clickableDebounced { onToggleCategoryMenu(true) },
                        shape = RoundedCornerShape(dims.corners.medium),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(dims.spacing.medium)
                        ) {
                            Icon(imageVector = Icons.Default.Folder, contentDescription = null, tint = primaryColor, modifier = Modifier.size(dims.icons.medium))
                            Spacer(modifier = Modifier.width(dims.spacing.small))
                            Column {
                                AppTypography.Caption(text = stringResource(R.string.task_editor_category))
                                AppTypography.BodyMedium(
                                    text = selectedCategoryName,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                        }
                    }

                    DropdownMenu(
                        expanded = showCategoryMenu,
                        onDismissRequest = { onToggleCategoryMenu(false) }
                    ) {
                        DropdownMenuItem(
                            text = { AppTypography.BodyMedium(text = stringResource(R.string.task_editor_none)) },
                            onClick = {
                                onSelectCategory(null)
                                onToggleCategoryMenu(false)
                            }
                        )
                        categoriesList.forEach { cat ->
                            DropdownMenuItem(
                                text = { AppTypography.BodyMedium(text = cat.name) },
                                onClick = {
                                    onSelectCategory(cat.id)
                                    onToggleCategoryMenu(false)
                                }
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(dims.spacing.medium))

            AppTypography.Caption(text = stringResource(R.string.task_editor_priority))
            Spacer(modifier = Modifier.height(dims.spacing.tiny))

            FlowRow(horizontalArrangement = Arrangement.spacedBy(dims.spacing.small)) {
                Priority.entries.forEach { priority ->
                    val isSelected = selectedPriority == priority
                    val chipColor = priority.color

                    FilterChip(
                        selected = isSelected,
                        onClick = { onSelectPriority(priority) },
                        label = { AppTypography.BodySmall(text = priority.label) },
                        shape = RoundedCornerShape(dims.corners.small),
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = chipColor,
                            selectedLabelColor = Color.White
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(dims.spacing.medium))

            AppTypography.Caption(text = stringResource(R.string.templates_relative_due))
            Spacer(modifier = Modifier.height(dims.spacing.tiny))

            val dayOptions = listOf(0, 1, 2, 3, 7, 14)
            FlowRow(horizontalArrangement = Arrangement.spacedBy(dims.spacing.small)) {
                dayOptions.forEach { days ->
                    val isSelected = relativeDueDays == days
                    val label = if (days == 0) {
                        stringResource(R.string.templates_due_today)
                    } else {
                        stringResource(R.string.templates_due_in_days, days)
                    }

                    FilterChip(
                        selected = isSelected,
                        onClick = { onSelectRelativeDays(days) },
                        label = { AppTypography.BodySmall(text = label) },
                        shape = RoundedCornerShape(dims.corners.small),
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = primaryColor,
                            selectedLabelColor = Color.White
                        )
                    )
                }
            }
        }
    }
}
