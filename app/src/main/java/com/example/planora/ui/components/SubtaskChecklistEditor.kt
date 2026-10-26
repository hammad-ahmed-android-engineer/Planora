package com.example.planora.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DeleteOutline
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.example.planora.R
import com.example.planora.domain.model.Subtask
import com.example.planora.ui.theme.EmeraldPrimary
import com.example.planora.ui.theme.dimensions
import com.example.planora.util.rememberDebouncedClick

@Composable
fun SubtaskChecklistEditor(
    subtasks: List<Subtask>,
    onAddSubtask: (String) -> Unit,
    onToggleSubtask: (Subtask) -> Unit,
    onDeleteSubtask: (Subtask) -> Unit,
    modifier: Modifier = Modifier
) {
    val dims = dimensions
    val primaryColor = MaterialTheme.colorScheme.primary
    var newSubtaskText by rememberSaveable { mutableStateOf("") }
    val progress = if (subtasks.isNotEmpty()) subtasks.count { it.isCompleted }.toFloat() / subtasks.size else 0f
    val animatedProgress by animateFloatAsState(
        targetValue = progress,
        animationSpec = spring(stiffness = 200f),
        label = "subtaskEditorProgress"
    )

    Column(modifier = modifier.fillMaxWidth()) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            AppTypography.TitleSmall(
                text = stringResource(R.string.task_detail_subtasks),
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.weight(1f))
            if (subtasks.isNotEmpty()) {
                AppTypography.SubText(
                    text = "${subtasks.count { it.isCompleted }}/${subtasks.size}",
                    color = primaryColor
                )
            }
        }

        if (subtasks.isNotEmpty()) {
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
        }

        Spacer(modifier = Modifier.height(dims.spacing.small))

        subtasks.forEach { subtask ->
            val debouncedDelete = rememberDebouncedClick { onDeleteSubtask(subtask) }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = dims.spacing.tiny)
            ) {
                AnimatedTaskCheckbox(
                    checked = subtask.isCompleted,
                    onCheckedChange = { onToggleSubtask(subtask) },
                    size = dims.icons.medium
                )
                Spacer(modifier = Modifier.width(dims.spacing.small))
                AppTypography.BodyMedium(
                    text = subtask.title,
                    color = if (subtask.isCompleted) MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f) else MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.weight(1f)
                )
                IconButton(
                    onClick = debouncedDelete,
                    modifier = Modifier.size(dims.components.buttonSmallHeight)
                ) {
                    Icon(
                        imageVector = Icons.Default.DeleteOutline,
                        contentDescription = stringResource(R.string.action_delete),
                        tint = MaterialTheme.colorScheme.error.copy(alpha = 0.7f),
                        modifier = Modifier.size(dims.icons.small)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(dims.spacing.small))

        val debouncedAdd = rememberDebouncedClick {
            if (newSubtaskText.isNotBlank()) {
                onAddSubtask(newSubtaskText.trim())
                newSubtaskText = ""
            }
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            OutlinedTextField(
                value = newSubtaskText,
                onValueChange = { newSubtaskText = it },
                placeholder = { AppTypography.BodyMedium(text = stringResource(R.string.templates_add_subtask_hint)) },
                singleLine = true,
                shape = RoundedCornerShape(dims.corners.medium),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = primaryColor,
                    unfocusedBorderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.35f)
                ),
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(dims.spacing.small))
            IconButton(
                onClick = debouncedAdd,
                modifier = Modifier.size(dims.components.avatar)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = null,
                    tint = primaryColor,
                    modifier = Modifier.size(dims.icons.medium)
                )
            }
        }
    }
}
