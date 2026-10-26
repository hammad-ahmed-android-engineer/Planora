package com.example.planora.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.DeleteOutline
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.PushPin
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.StarBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.example.planora.R
import com.example.planora.domain.model.Priority
import com.example.planora.domain.model.Task
import com.example.planora.ui.theme.EmeraldPrimary
import com.example.planora.ui.theme.FlameYellow
import com.example.planora.ui.theme.IndigoPrimary
import com.example.planora.ui.theme.dimensions
import com.example.planora.util.color
import com.example.planora.util.formatIsoDateToDisplay
import com.example.planora.util.formatIsoTimeToDisplay
import com.example.planora.util.clickableDebounced

@Composable
fun TaskItemCard(
    task: Task,
    onTaskClick: (Task) -> Unit,
    onToggleComplete: (Task) -> Unit,
    onToggleFavorite: (Task) -> Unit,
    onDeleteTask: (Task) -> Unit,
    onPostponeTask: (Task) -> Unit,
    onTogglePin: ((Task) -> Unit)? = null,
    onEditTask: ((Task) -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    var showMenu by rememberSaveable { mutableStateOf(false) }

    val priorityColor = task.priority.color

    val cardBgColor by animateColorAsState(
        targetValue = if (task.isCompleted) {
            MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.35f)
        } else {
            MaterialTheme.colorScheme.surface
        },
        animationSpec = tween(250),
        label = "cardBgColor"
    )

    val contentAlpha by animateFloatAsState(
        targetValue = if (task.isCompleted) 0.55f else 1f,
        animationSpec = tween(250),
        label = "contentAlpha"
    )

    val starScale by animateFloatAsState(
        targetValue = if (task.isStarred) 1.15f else 1f,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy),
        label = "starScale"
    )

    val animatedSubtaskProgress by animateFloatAsState(
        targetValue = task.subtaskProgress,
        animationSpec = spring(stiffness = Spring.StiffnessLow),
        label = "subtaskProgress"
    )

    val dims = dimensions
    val primaryColor = MaterialTheme.colorScheme.primary
    val shape = RoundedCornerShape(dims.corners.large)

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = dims.spacing.tiny, horizontal = dims.spacing.medium)
            .clip(shape)
            .border(
                width = dims.borders.thin,
                color = if (task.isStarred) primaryColor.copy(alpha = 0.3f) else MaterialTheme.colorScheme.outline.copy(alpha = 0.35f),
                shape = shape
            )
            .clickableDebounced { onTaskClick(task) },
        shape = shape,
        colors = CardDefaults.cardColors(containerColor = cardBgColor),
        elevation = CardDefaults.cardElevation(defaultElevation = if (task.isCompleted) dims.elevations.none else dims.elevations.low)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(dims.spacing.medium),
            verticalAlignment = Alignment.Top
        ) {
            // Left Priority Indicator Pill
            Box(
                modifier = Modifier
                    .width(dims.borders.thick)
                    .height(dims.components.buttonHeight)
                    .clip(RoundedCornerShape(dims.corners.micro))
                    .background(priorityColor)
            )

            Spacer(modifier = Modifier.width(dims.spacing.small))

            // Animated Custom Checkbox
            Box(modifier = Modifier.padding(top = dims.spacing.micro)) {
                AnimatedTaskCheckbox(
                    checked = task.isCompleted,
                    onCheckedChange = { onToggleComplete(task) }
                )
            }

            Spacer(modifier = Modifier.width(dims.spacing.medium))

            // Task Content
            Column(
                modifier = Modifier
                    .weight(1f)
                    .alpha(contentAlpha)
            ) {
                // Title
                AppTypography.TitleMedium(
                    text = task.title,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 2
                )

                // Optional Description
                if (task.description.isNotBlank()) {
                    Spacer(modifier = Modifier.height(dims.spacing.tiny))
                    AppTypography.BodySmall(
                        text = task.description,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        maxLines = 2
                    )
                }

                // Subtask Progress Indicator
                if (task.subtasks.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(dims.spacing.small))
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        LinearProgressIndicator(
                            progress = { animatedSubtaskProgress },
                            modifier = Modifier
                                .weight(1f)
                                .height(dims.spacing.micro)
                                .clip(RoundedCornerShape(dims.corners.micro)),
                            color = if (task.isCompleted) EmeraldPrimary else primaryColor,
                            trackColor = MaterialTheme.colorScheme.surfaceVariant
                        )
                        Spacer(modifier = Modifier.width(dims.spacing.small))
                        AppTypography.Caption(
                            text = "${task.subtasks.count { it.isCompleted }}/${task.subtasks.size}",
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                // Metadata Badges (Category, Priority, Due Date)
                Spacer(modifier = Modifier.height(dims.spacing.small))
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(dims.spacing.tiny),
                    verticalArrangement = Arrangement.spacedBy(dims.spacing.tiny),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    if (task.isPinned) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .clip(RoundedCornerShape(dims.corners.small))
                                .background(primaryColor.copy(alpha = 0.12f))
                                .padding(horizontal = dims.spacing.small, vertical = dims.spacing.micro)
                        ) {
                            Icon(
                                imageVector = Icons.Default.PushPin,
                                contentDescription = stringResource(R.string.action_pin),
                                tint = primaryColor,
                                modifier = Modifier.size(dims.icons.micro)
                            )
                            Spacer(modifier = Modifier.width(dims.spacing.micro))
                            AppTypography.Caption(
                                text = stringResource(R.string.tab_pinned),
                                fontWeight = FontWeight.Bold,
                                color = primaryColor
                            )
                        }
                    }

                    task.category?.let { cat ->
                        CategoryChip(category = cat)
                    }

                    if (task.priority != Priority.NONE) {
                        PriorityBadge(priority = task.priority)
                    }

                    task.dueDate?.let { dueDateStr ->
                        val isOverdue = task.isOverdue
                        val badgeBg = if (isOverdue) {
                            MaterialTheme.colorScheme.error.copy(alpha = 0.12f)
                        } else {
                            MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f)
                        }
                        val badgeColor = if (isOverdue) {
                            MaterialTheme.colorScheme.error
                        } else {
                            MaterialTheme.colorScheme.onSurfaceVariant
                        }

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .clip(RoundedCornerShape(dims.corners.small))
                                .background(badgeBg)
                                .padding(horizontal = dims.spacing.small, vertical = dims.spacing.micro)
                        ) {
                            Icon(
                                imageVector = if (isOverdue) Icons.Default.AccessTime else Icons.Default.CalendarToday,
                                contentDescription = null,
                                tint = badgeColor,
                                modifier = Modifier.size(dims.icons.micro)
                            )
                            Spacer(modifier = Modifier.width(dims.spacing.tiny))
                            AppTypography.Caption(
                                text = if (task.dueTime != null) "${dueDateStr.formatIsoDateToDisplay()}, ${task.dueTime.formatIsoTimeToDisplay()}" else dueDateStr.formatIsoDateToDisplay(),
                                fontWeight = if (isOverdue) FontWeight.Bold else FontWeight.Medium,
                                color = badgeColor
                            )
                        }
                    }
                }
            }

            // Right Quick Actions: Star & More Menu
            val debouncedFavorite = com.example.planora.util.rememberDebouncedClick { onToggleFavorite(task) }
            val debouncedEdit = com.example.planora.util.rememberDebouncedClick {
                showMenu = false
                if (onEditTask != null) {
                    onEditTask(task)
                } else {
                    onTaskClick(task)
                }
            }
            val debouncedPin = com.example.planora.util.rememberDebouncedClick {
                showMenu = false
                onTogglePin?.invoke(task)
            }
            val debouncedPostpone = com.example.planora.util.rememberDebouncedClick {
                showMenu = false
                onPostponeTask(task)
            }
            val debouncedDelete = com.example.planora.util.rememberDebouncedClick {
                showMenu = false
                onDeleteTask(task)
            }

            Column(
                horizontalAlignment = Alignment.End,
                modifier = Modifier.padding(start = dims.spacing.tiny)
            ) {
                IconButton(
                    onClick = debouncedFavorite,
                    modifier = Modifier.size(dims.components.buttonSmallHeight)
                ) {
                    Icon(
                        imageVector = if (task.isStarred) Icons.Default.Star else Icons.Outlined.StarBorder,
                        contentDescription = stringResource(R.string.action_favorite),
                        tint = if (task.isStarred) FlameYellow else MaterialTheme.colorScheme.outline.copy(alpha = 0.5f),
                        modifier = Modifier
                            .size(dims.icons.medium)
                            .scale(starScale)
                    )
                }

                Box {
                    IconButton(
                        onClick = { showMenu = true },
                        modifier = Modifier.size(dims.components.buttonSmallHeight)
                    ) {
                        Icon(
                            imageVector = Icons.Default.MoreVert,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f),
                            modifier = Modifier.size(dims.icons.small)
                        )
                    }

                    DropdownMenu(
                        expanded = showMenu,
                        onDismissRequest = { showMenu = false }
                    ) {
                        DropdownMenuItem(
                            text = { AppTypography.BodyMedium(text = stringResource(R.string.action_edit)) },
                            leadingIcon = { Icon(Icons.Default.Edit, contentDescription = null) },
                            onClick = debouncedEdit
                        )
                        DropdownMenuItem(
                            text = { AppTypography.BodyMedium(text = stringResource(if (task.isPinned) R.string.action_unpin else R.string.action_pin)) },
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Default.PushPin,
                                    contentDescription = null,
                                    tint = if (task.isPinned) primaryColor else MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            },
                            onClick = debouncedPin
                        )
                        DropdownMenuItem(
                            text = { AppTypography.BodyMedium(text = stringResource(R.string.action_postpone_tomorrow)) },
                            leadingIcon = { Icon(Icons.Default.Schedule, contentDescription = null) },
                            onClick = debouncedPostpone
                        )
                        DropdownMenuItem(
                            text = { AppTypography.BodyMedium(text = stringResource(R.string.action_delete), color = MaterialTheme.colorScheme.error) },
                            leadingIcon = { Icon(Icons.Default.DeleteOutline, contentDescription = null, tint = MaterialTheme.colorScheme.error) },
                            onClick = debouncedDelete
                        )
                    }
                }
            }
        }
    }
}
