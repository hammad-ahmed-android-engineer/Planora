package com.example.planora.ui.screens.taskeditor.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.planora.R
import com.example.planora.ui.components.AppTypography
import com.example.planora.ui.theme.dimensions

@Composable
fun TaskDescriptionField(
    description: String,
    onDescriptionChange: (String) -> Unit,
    minLines: Int = 2,
    maxLines: Int = 4,
    modifier: Modifier = Modifier
) {
    val dims = dimensions
    val primaryColor = MaterialTheme.colorScheme.primary

    OutlinedTextField(
        value = description,
        onValueChange = onDescriptionChange,
        placeholder = {
            AppTypography.BodyMedium(
                text = stringResource(R.string.task_editor_desc_hint),
                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)
            )
        },
        minLines = minLines,
        maxLines = maxLines,
        shape = RoundedCornerShape(dims.corners.large),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = primaryColor,
            unfocusedBorderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.35f)
        ),
        modifier = modifier.fillMaxWidth()
    )
}
