package com.example.planora.presentation.taskeditor.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.planora.R
import com.example.planora.core.designsystem.components.AppOutlinedTextField

@Composable
fun TaskDescriptionField(
    description: String,
    onDescriptionChange: (String) -> Unit,
    minLines: Int = 2,
    maxLines: Int = 4,
    modifier: Modifier = Modifier
) {
    AppOutlinedTextField(
        value = description,
        onValueChange = onDescriptionChange,
        placeholder = stringResource(R.string.task_editor_desc_hint),
        singleLine = false,
        maxLines = maxLines,
        modifier = modifier.fillMaxWidth()
    )
}
