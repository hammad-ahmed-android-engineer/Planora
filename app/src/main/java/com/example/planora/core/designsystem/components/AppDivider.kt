package com.example.planora.core.designsystem.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import com.example.planora.core.designsystem.theme.dimensions

/**
 * Standardized divider component with configurable thickness, color, and padding.
 */
@Composable
fun AppDivider(
    modifier: Modifier = Modifier,
    thickness: Dp = dimensions.borders.thin,
    color: Color = MaterialTheme.colorScheme.outline.copy(alpha = 0.15f),
    verticalPadding: Dp = dimensions.spacing.zero
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = verticalPadding)
            .height(thickness)
            .background(color)
    )
}
