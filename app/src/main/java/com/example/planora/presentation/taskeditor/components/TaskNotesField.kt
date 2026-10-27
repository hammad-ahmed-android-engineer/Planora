package com.example.planora.presentation.taskeditor.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.planora.R
import com.example.planora.core.designsystem.components.AppOutlinedTextField

@Composable
fun TaskNotesField(
    notes: String,
    onNotesChange: (String) -> Unit,
    maxLines: Int = 3,
    modifier: Modifier = Modifier
) {
    AppOutlinedTextField(
        value = notes,
        onValueChange = onNotesChange,
        label = stringResource(R.string.task_detail_notes),
        placeholder = stringResource(R.string.task_editor_notes_hint),
        singleLine = false,
        maxLines = maxLines,
        modifier = modifier.fillMaxWidth()
    )
}
