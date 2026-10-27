package com.example.planora.presentation.taskeditor.components

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.example.planora.R
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

    TopAppBar(
        title = {
            AppTypography.TitleLarge(
                text = if (isEditMode) stringResource(R.string.task_editor_edit_title) else stringResource(R.string.task_editor_create_title)
            )
        },
        navigationIcon = {
            IconButton(onClick = debouncedBack) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = stringResource(R.string.back),
                    tint = primaryColor
                )
            }
        },
        actions = {
            Button(
                onClick = debouncedSave,
                enabled = canSave,
                colors = ButtonDefaults.buttonColors(containerColor = primaryColor),
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
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.background
        ),
        modifier = modifier
    )
}
