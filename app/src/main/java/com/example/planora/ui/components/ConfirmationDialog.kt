package com.example.planora.ui.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.example.planora.R
import com.example.planora.util.rememberDebouncedClick

@Composable
fun ConfirmationDialog(
    title: String,
    message: String,
    confirmText: String = stringResource(R.string.confirm),
    dismissText: String = stringResource(R.string.cancel),
    isDestructive: Boolean = false,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    val debouncedConfirm = rememberDebouncedClick(onClick = onConfirm)
    val debouncedDismiss = rememberDebouncedClick(onClick = onDismiss)

    AlertDialog(
        onDismissRequest = debouncedDismiss,
        title = {
            AppTypography.TitleMedium(
                text = title,
                fontWeight = FontWeight.Bold
            )
        },
        text = {
            AppTypography.BodyMedium(
                text = message,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        },
        confirmButton = {
            Button(
                onClick = debouncedConfirm,
                colors = if (isDestructive) {
                    ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
                } else {
                    ButtonDefaults.buttonColors()
                }
            ) {
                AppTypography.BodyMedium(
                    text = confirmText,
                    fontWeight = FontWeight.Bold
                )
            }
        },
        dismissButton = {
            TextButton(onClick = debouncedDismiss) {
                AppTypography.BodyMedium(
                    text = dismissText,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    )
}
