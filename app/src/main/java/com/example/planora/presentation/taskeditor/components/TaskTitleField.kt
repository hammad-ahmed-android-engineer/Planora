package com.example.planora.presentation.taskeditor.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.planora.R
import com.example.planora.core.designsystem.components.AppOutlinedTextField

@Composable
fun TaskTitleField(
    title: String,
    onTitleChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    AppOutlinedTextField(
        value = title,
        onValueChange = onTitleChange,
        placeholder = stringResource(R.string.task_editor_title_hint),
        singleLine = true,
        modifier = modifier.fillMaxWidth()
    )
}
