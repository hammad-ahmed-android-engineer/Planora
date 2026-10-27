package com.example.planora.core.designsystem.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import com.example.planora.core.designsystem.theme.AppTypography
import com.example.planora.core.utils.rememberDebouncedClick

@Composable
fun AppTextButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    color: Color = MaterialTheme.colorScheme.primary
) {
    val debouncedClick = rememberDebouncedClick(onClick = onClick)

    TextButton(
        onClick = debouncedClick,
        modifier = modifier,
        enabled = enabled
    ) {
        AppTypography.BodyMedium(
            text = text,
            color = if (enabled) color else MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.4f),
            fontWeight = FontWeight.Bold
        )
    }
}
