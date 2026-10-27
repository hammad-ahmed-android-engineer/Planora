package com.example.planora.core.designsystem.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.planora.core.designsystem.theme.AppTypography
import com.example.planora.core.designsystem.theme.dimensions
import com.example.planora.core.utils.rememberDebouncedClick

/**
 * Standardized, highly customizable Design System Modal Dialog with extensive configuration options:
 * title, subtitle, custom icon, body content, action buttons, shape, container colors, and dismiss properties.
 */
@Composable
fun AppDialog(
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    title: String? = null,
    subtitle: String? = null,
    icon: ImageVector? = null,
    iconTint: Color = MaterialTheme.colorScheme.primary,
    confirmButtonText: String? = null,
    onConfirm: (() -> Unit)? = null,
    dismissButtonText: String? = null,
    onDismiss: (() -> Unit)? = onDismissRequest,
    isDestructive: Boolean = false,
    confirmButtonEnabled: Boolean = true,
    shape: Shape = RoundedCornerShape(dimensions.corners.extraLarge),
    containerColor: Color = MaterialTheme.colorScheme.surface,
    properties: DialogProperties = DialogProperties(dismissOnBackPress = true, dismissOnClickOutside = true),
    content: (@Composable () -> Unit)? = null
) {
    val dims = dimensions
    val debouncedDismiss = rememberDebouncedClick(onClick = onDismissRequest)
    val debouncedConfirm = onConfirm?.let { rememberDebouncedClick(onClick = it) }
    val debouncedDismissAction = onDismiss?.let { rememberDebouncedClick(onClick = it) }

    Dialog(
        onDismissRequest = debouncedDismiss,
        properties = properties
    ) {
        Card(
            modifier = modifier
                .fillMaxWidth()
                .padding(dims.spacing.default),
            shape = shape,
            colors = CardDefaults.cardColors(containerColor = containerColor),
            elevation = CardDefaults.cardElevation(defaultElevation = dims.elevations.medium)
        ) {
            Column(
                modifier = Modifier.padding(dims.spacing.large),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (icon != null) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .size(dims.components.fab)
                            .clip(CircleShape)
                            .background(iconTint.copy(alpha = 0.12f))
                    ) {
                        Icon(
                            imageVector = icon,
                            contentDescription = null,
                            tint = iconTint,
                            modifier = Modifier.size(dims.icons.large)
                        )
                    }
                    Spacer(modifier = Modifier.height(dims.spacing.default))
                }

                if (title != null) {
                    AppTypography.TitleLarge(
                        text = title,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(dims.spacing.small))
                }

                if (subtitle != null) {
                    AppTypography.BodyMedium(
                        text = subtitle,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(dims.spacing.medium))
                }

                if (content != null) {
                    content()
                    Spacer(modifier = Modifier.height(dims.spacing.medium))
                }

                if (confirmButtonText != null || dismissButtonText != null) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        if (dismissButtonText != null && debouncedDismissAction != null) {
                            AppTextButton(
                                text = dismissButtonText,
                                onClick = debouncedDismissAction,
                                modifier = Modifier.weight(1f)
                            )
                        }

                        if (dismissButtonText != null && confirmButtonText != null) {
                            Spacer(modifier = Modifier.width(dims.spacing.small))
                        }

                        if (confirmButtonText != null && debouncedConfirm != null) {
                            AppButton(
                                text = confirmButtonText,
                                onClick = debouncedConfirm,
                                enabled = confirmButtonEnabled,
                                containerColor = if (isDestructive) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary,
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }
                }
            }
        }
    }
}
