package com.example.planora.core.designsystem.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import com.example.planora.R
import com.example.planora.core.designsystem.theme.dimensions

@Composable
fun ConfirmationDialog(
    title: String,
    message: String,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
    icon: ImageVector? = null,
    confirmText: String = stringResource(R.string.confirm),
    dismissText: String = stringResource(R.string.cancel),
    isDestructive: Boolean = false,
    shape: Shape = androidx.compose.foundation.shape.RoundedCornerShape(dimensions.corners.extraLarge),
    containerColor: Color = MaterialTheme.colorScheme.surface
) {
    AppDialog(
        onDismissRequest = onDismiss,
        modifier = modifier,
        title = title,
        subtitle = message,
        icon = icon,
        confirmButtonText = confirmText,
        onConfirm = onConfirm,
        dismissButtonText = dismissText,
        onDismiss = onDismiss,
        isDestructive = isDestructive,
        shape = shape,
        containerColor = containerColor
    )
}
