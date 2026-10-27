package com.example.planora.presentation.templates.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.example.planora.R
import com.example.planora.core.designsystem.components.AppCard
import com.example.planora.core.designsystem.components.AppIconButton
import com.example.planora.core.designsystem.components.AppOutlinedTextField
import com.example.planora.core.designsystem.theme.AppTypography
import com.example.planora.core.designsystem.theme.dimensions
import com.example.planora.core.utils.rememberDebouncedClick

@Composable
fun TemplateSubtasksSection(
    subtasks: List<String>,
    newSubtaskText: String,
    onNewSubtaskChange: (String) -> Unit,
    onAddSubtask: () -> Unit,
    onDeleteSubtask: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val dims = dimensions
    val primaryColor = MaterialTheme.colorScheme.primary
    val debouncedAdd = rememberDebouncedClick(onClick = onAddSubtask)

    AppCard(
        modifier = modifier.fillMaxWidth(),
        containerColor = MaterialTheme.colorScheme.surface,
        borderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.25f)
    ) {
        Column(modifier = Modifier.padding(dims.spacing.default)) {
            AppTypography.TitleMedium(
                text = stringResource(R.string.templates_subtasks_header),
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(dims.spacing.small))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                AppOutlinedTextField(
                    value = newSubtaskText,
                    onValueChange = onNewSubtaskChange,
                    placeholder = stringResource(R.string.templates_add_subtask_hint),
                    singleLine = true,
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(dims.spacing.small))
                AppIconButton(
                    onClick = debouncedAdd,
                    enabled = newSubtaskText.isNotBlank(),
                    icon = Icons.Default.Add,
                    contentDescription = stringResource(R.string.confirm),
                    tint = if (newSubtaskText.isNotBlank()) primaryColor else MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.3f)
                )
            }

            if (subtasks.isNotEmpty()) {
                Spacer(modifier = Modifier.height(dims.spacing.medium))

                subtasks.forEachIndexed { index, subtask ->
                    val debouncedDelete = rememberDebouncedClick { onDeleteSubtask(index) }
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = dims.spacing.tiny)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(dims.icons.micro)
                                .clip(CircleShape)
                                .background(primaryColor)
                        )
                        Spacer(modifier = Modifier.width(dims.spacing.small))
                        AppTypography.BodyMedium(
                            text = subtask,
                            modifier = Modifier.weight(1f)
                        )
                        AppIconButton(
                            onClick = debouncedDelete,
                            icon = Icons.Default.Close,
                            contentDescription = stringResource(R.string.delete),
                            tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)
                        )
                    }
                }
            }
        }
    }
}
