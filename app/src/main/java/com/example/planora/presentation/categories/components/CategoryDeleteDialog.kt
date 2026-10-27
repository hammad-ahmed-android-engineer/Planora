package com.example.planora.presentation.categories.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.example.planora.R
import com.example.planora.core.designsystem.components.AppButton
import com.example.planora.core.designsystem.components.AppTextButton
import com.example.planora.core.designsystem.theme.AppTypography
import com.example.planora.core.utils.rememberDebouncedClick
import com.example.planora.domain.model.Category

@Composable
fun CategoryDeleteDialog(
    category: Category,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    val debouncedConfirm = rememberDebouncedClick(onClick = onConfirm)
    val debouncedDismiss = rememberDebouncedClick(onClick = onDismiss)

    AlertDialog(
        onDismissRequest = debouncedDismiss,
        title = {
            AppTypography.TitleMedium(
                text = stringResource(R.string.category_delete_title),
                fontWeight = FontWeight.Bold
            )
        },
        text = {
            AppTypography.BodyMedium(
                text = stringResource(R.string.category_delete_msg, category.name)
            )
        },
        confirmButton = {
            AppButton(
                text = stringResource(R.string.delete),
                onClick = debouncedConfirm,
                containerColor = MaterialTheme.colorScheme.error
            )
        },
        dismissButton = {
            AppTextButton(
                text = stringResource(R.string.cancel),
                onClick = debouncedDismiss
            )
        }
    )
}
