package com.example.planora.ui.screens.taskeditor.components

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
import androidx.compose.material.icons.filled.PushPin
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
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
fun OrganizationSectionCard(
    selectedCategory: Category?,
    availableCategories: List<Category>,
    selectedPriority: Priority,
    isPinned: Boolean,
    onSelectCategory: (Category?) -> Unit,
    onSelectPriority: (Priority) -> Unit,
    onTogglePinned: (Boolean) -> Unit,
    showCategoryMenu: Boolean,
    onToggleCategoryMenu: (Boolean) -> Unit,
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

            Box {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(dims.corners.medium))
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
                                text = selectedCategory?.name ?: stringResource(R.string.task_editor_uncategorized),
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
                    availableCategories.forEach { cat ->
                        DropdownMenuItem(
                            text = { AppTypography.BodyMedium(text = cat.name) },
                            onClick = {
                                onSelectCategory(cat)
                                onToggleCategoryMenu(false)
                            }
                        )
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

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(imageVector = Icons.Default.PushPin, contentDescription = null, tint = primaryColor, modifier = Modifier.size(dims.icons.medium))
                    Spacer(modifier = Modifier.width(dims.spacing.small))
                    AppTypography.BodyMedium(text = stringResource(R.string.task_editor_pin_task), fontWeight = FontWeight.SemiBold)
                }

                Switch(
                    checked = isPinned,
                    onCheckedChange = onTogglePinned,
                    colors = SwitchDefaults.colors(checkedThumbColor = Color.White, checkedTrackColor = primaryColor)
                )
            }
        }
    }
}
