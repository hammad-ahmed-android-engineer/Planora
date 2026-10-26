package com.example.planora.ui.screens.templates.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.example.planora.R
import com.example.planora.ui.components.AppTypography
import com.example.planora.ui.theme.dimensions
import com.example.planora.util.rememberDebouncedClick

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
    val shape = RoundedCornerShape(dims.corners.extraLarge)
    val debouncedAdd = rememberDebouncedClick(onClick = onAddSubtask)

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
                text = stringResource(R.string.templates_subtasks_header),
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(dims.spacing.small))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedTextField(
                    value = newSubtaskText,
                    onValueChange = onNewSubtaskChange,
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
                    enabled = newSubtaskText.isNotBlank()
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = stringResource(R.string.confirm),
                        tint = if (newSubtaskText.isNotBlank()) primaryColor else MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.3f),
                        modifier = Modifier.size(dims.icons.medium)
                    )
                }
            }

            if (subtasks.isNotEmpty()) {
                Spacer(modifier = Modifier.height(dims.spacing.medium))

                subtasks.forEachIndexed { index, subtask ->
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
                        IconButton(onClick = { onDeleteSubtask(index) }) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = stringResource(R.string.delete),
                                tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f),
                                modifier = Modifier.size(dims.icons.small)
                            )
                        }
                    }
                }
            }
        }
    }
}
