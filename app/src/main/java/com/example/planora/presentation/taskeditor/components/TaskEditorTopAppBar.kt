package com.example.planora.presentation.taskeditor.components

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.example.planora.R
import com.example.planora.core.designsystem.components.AppButton
import com.example.planora.core.designsystem.components.AppTopAppBar
import com.example.planora.core.designsystem.theme.AppTypography
import com.example.planora.core.designsystem.theme.dimensions
import com.example.planora.core.utils.rememberDebouncedClick

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskEditorTopAppBar(
    isEditMode: Boolean,
    canSave: Boolean,
    onNavigateBack: () -> Unit,
    onSaveTask: () -> Unit,
    modifier: Modifier = Modifier
) {
    val dims = dimensions
    val primaryColor = MaterialTheme.colorScheme.primary
    val debouncedBack = rememberDebouncedClick(onClick = onNavigateBack)
    val debouncedSave = rememberDebouncedClick(onClick = onSaveTask)

    AppTopAppBar(
        title = if (isEditMode) stringResource(R.string.task_editor_edit_title) else stringResource(R.string.task_editor_create_title),
        onNavigateBack = debouncedBack,
        navigationIconColor = primaryColor,
        actions = {
            AppButton(
                onClick = debouncedSave,
                enabled = canSave,
                containerColor = primaryColor,
                contentColor = Color.White,
                shape = RoundedCornerShape(dims.corners.medium)
            ) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(dims.icons.small)
                )
                AppTypography.BodyMedium(
                    text = if (isEditMode) stringResource(R.string.task_editor_save) else stringResource(R.string.task_editor_create),
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }
        },
        modifier = modifier
    )
}
