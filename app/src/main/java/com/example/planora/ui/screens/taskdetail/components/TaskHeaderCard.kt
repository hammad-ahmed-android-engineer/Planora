package com.example.planora.ui.screens.taskdetail.components

import androidx.compose.foundation.border
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import com.example.planora.domain.model.Priority
import com.example.planora.domain.model.Task
import com.example.planora.ui.components.AnimatedTaskCheckbox
import com.example.planora.ui.components.AppTypography
import com.example.planora.ui.components.CategoryChip
import com.example.planora.ui.components.PriorityBadge
import com.example.planora.ui.theme.dimensions

@Composable
fun TaskHeaderCard(
    task: Task,
    onToggleComplete: () -> Unit,
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
            Row(
                verticalAlignment = Alignment.Top,
                modifier = Modifier.fillMaxWidth()
            ) {
                AnimatedTaskCheckbox(
                    checked = task.isCompleted,
                    onCheckedChange = { onToggleComplete() }
                )

                Spacer(modifier = Modifier.width(dims.spacing.medium))

                Column(modifier = Modifier.weight(1f)) {
                    AppTypography.TitleLarge(
                        text = task.title,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    if (task.description.isNotBlank()) {
                        Spacer(modifier = Modifier.height(dims.spacing.tiny))
                        AppTypography.BodyMedium(
                            text = task.description,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(dims.spacing.medium))

            Row(verticalAlignment = Alignment.CenterVertically) {
                task.category?.let { cat ->
                    CategoryChip(category = cat)
                    Spacer(modifier = Modifier.width(dims.spacing.small))
                }

                if (task.priority != Priority.NONE) {
                    PriorityBadge(priority = task.priority)
                }
            }
        }
    }
}
