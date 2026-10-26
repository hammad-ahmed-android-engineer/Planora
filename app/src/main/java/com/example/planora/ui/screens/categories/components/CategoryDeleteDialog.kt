package com.example.planora.ui.screens.categories.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.example.planora.R
import com.example.planora.domain.model.Category
import com.example.planora.ui.components.AppTypography
import com.example.planora.util.rememberDebouncedClick

@Composable
fun CategoryDeleteDialog(
    category: Category,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    val debouncedConfirm = rememberDebouncedClick(onClick = onConfirm)

    AlertDialog(
        onDismissRequest = onDismiss,
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
            Button(
                onClick = debouncedConfirm,
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
            ) {
                AppTypography.BodyMedium(text = stringResource(R.string.delete), color = Color.White)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                AppTypography.BodyMedium(text = stringResource(R.string.cancel))
            }
        }
    )
}
