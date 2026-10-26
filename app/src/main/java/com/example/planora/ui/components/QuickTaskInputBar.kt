package com.example.planora.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.example.planora.R
import com.example.planora.domain.usecase.NaturalLanguageTaskParser
import com.example.planora.ui.theme.dimensions
import com.example.planora.util.isValidTitle
import com.example.planora.util.rememberDebouncedClick

@Composable
fun QuickTaskInputBar(
    onAddTask: (title: String, dueDate: String?, dueTime: String?, priorityName: String, tags: List<String>) -> Unit,
    modifier: Modifier = Modifier
) {
    val dims = dimensions
    val primaryColor = MaterialTheme.colorScheme.primary
    var text by rememberSaveable { mutableStateOf("") }
    val parsed = remember(text) { NaturalLanguageTaskParser.parse(text) }

    val shape = RoundedCornerShape(dims.corners.extraLarge)

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = dims.spacing.medium, vertical = dims.spacing.small)
            .border(dims.borders.thin, MaterialTheme.colorScheme.outline.copy(alpha = 0.4f), shape),
        shape = shape,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = dims.elevations.medium)
    ) {
        Column(modifier = Modifier.padding(horizontal = dims.spacing.medium, vertical = dims.spacing.small)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedTextField(
                    value = text,
                    onValueChange = { text = it },
                    placeholder = {
                        AppTypography.BodyMedium(
                            text = stringResource(R.string.quick_add_placeholder),
                            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f),
                            maxLines = 1
                        )
                    },
                    singleLine = true,
                    textStyle = MaterialTheme.typography.bodyLarge,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color.Transparent,
                        unfocusedBorderColor = Color.Transparent,
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent
                    ),
                    modifier = Modifier.weight(1f)
                )

                Spacer(modifier = Modifier.width(dims.spacing.small))

                val isInputValid = text.isValidTitle()
                val debouncedAdd = rememberDebouncedClick {
                    if (isInputValid) {
                        onAddTask(
                            parsed.title,
                            parsed.dueDate,
                            parsed.dueTime,
                            parsed.priority.name,
                            parsed.tagNames
                        )
                        text = ""
                    }
                }

                IconButton(
                    onClick = debouncedAdd,
                    enabled = isInputValid,
                    modifier = Modifier
                        .size(dims.icons.huge)
                        .clip(CircleShape)
                        .background(
                            if (isInputValid) primaryColor else MaterialTheme.colorScheme.surfaceVariant
                        )
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.Send,
                        contentDescription = stringResource(R.string.home_add_task),
                        tint = if (isInputValid) Color.White else MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.size(dims.icons.medium)
                    )
                }
            }
        }
    }
}
