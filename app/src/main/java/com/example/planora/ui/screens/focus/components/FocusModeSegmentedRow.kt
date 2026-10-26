package com.example.planora.ui.screens.focus.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import com.example.planora.ui.components.AppTypography
import com.example.planora.ui.screens.focus.FocusMode
import com.example.planora.ui.theme.IndigoPrimary
import com.example.planora.ui.theme.dimensions
import com.example.planora.util.clickableDebounced

@Composable
fun FocusModeSegmentedRow(
    currentMode: FocusMode,
    onModeSelected: (FocusMode) -> Unit,
    modifier: Modifier = Modifier
) {
    val dims = dimensions
    val shape = RoundedCornerShape(dims.corners.large)

    Row(
        modifier = modifier
            .clip(shape)
            .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
            .border(dims.borders.thin, MaterialTheme.colorScheme.outline.copy(alpha = 0.3f), shape)
            .padding(dims.spacing.micro),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        FocusMode.entries.forEach { mode ->
            val isSelected = currentMode == mode
            val bgColor by animateColorAsState(
                targetValue = if (isSelected) IndigoPrimary else Color.Transparent,
                animationSpec = tween(200),
                label = "modeBg"
            )
            val textColor by animateColorAsState(
                targetValue = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurfaceVariant,
                animationSpec = tween(200),
                label = "modeText"
            )

            Box(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(dims.corners.medium))
                    .background(bgColor)
                    .clickableDebounced { onModeSelected(mode) }
                    .padding(vertical = dims.spacing.small),
                contentAlignment = Alignment.Center
            ) {
                AppTypography.SubText(
                    text = mode.label,
                    color = textColor
                )
            }
        }
    }
}
