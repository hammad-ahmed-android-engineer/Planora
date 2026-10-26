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
fun TaskNotesField(
    notes: String,
    onNotesChange: (String) -> Unit,
    maxLines: Int = 3,
    modifier: Modifier = Modifier
) {
    val dims = dimensions
    val primaryColor = MaterialTheme.colorScheme.primary

    OutlinedTextField(
        value = notes,
        onValueChange = onNotesChange,
        label = { AppTypography.BodyMedium(text = stringResource(R.string.task_detail_notes)) },
        placeholder = { AppTypography.BodySmall(text = stringResource(R.string.task_editor_notes_hint)) },
        maxLines = maxLines,
        shape = RoundedCornerShape(dims.corners.large),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = primaryColor,
            unfocusedBorderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.35f)
        ),
        modifier = modifier.fillMaxWidth()
    )
}
