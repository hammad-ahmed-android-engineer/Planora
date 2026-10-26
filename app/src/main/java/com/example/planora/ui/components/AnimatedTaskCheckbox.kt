package com.example.planora.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import com.example.planora.ui.theme.EmeraldPrimary
import com.example.planora.ui.theme.IndigoPrimary
import com.example.planora.ui.theme.dimensions
import com.example.planora.util.clickableDebounced

@Composable
fun AnimatedTaskCheckbox(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    size: Dp? = null
) {
    val dims = dimensions
    val checkboxSize = size ?: dims.icons.large

    val scale by animateFloatAsState(
        targetValue = if (checked) 1f else 0.95f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMedium
        ),
        label = "checkboxScale"
    )

    val checkIconScale by animateFloatAsState(
        targetValue = if (checked) 1f else 0f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioLowBouncy,
            stiffness = Spring.StiffnessMediumLow
        ),
        label = "iconScale"
    )

    val borderColor by animateColorAsState(
        targetValue = if (checked) Color.Transparent else MaterialTheme.colorScheme.outline.copy(alpha = 0.6f),
        animationSpec = tween(200, easing = FastOutSlowInEasing),
        label = "borderColor"
    )

    val shape = RoundedCornerShape(dims.corners.small)

    Box(
        modifier = modifier
            .size(checkboxSize)
            .scale(scale)
            .clip(shape)
            .then(
                if (checked) {
                    Modifier.background(
                        Brush.linearGradient(
                            listOf(
                                IndigoPrimary,
                                EmeraldPrimary
                            )
                        )
                    )
                } else {
                    Modifier
                        .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f))
                        .border(dims.borders.thin, borderColor, shape)
                }
            )
            .clickableDebounced {
                onCheckedChange(!checked)
            },
        contentAlignment = Alignment.Center
    ) {
        if (checked || checkIconScale > 0.05f) {
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = if (checked) "Checked" else "Unchecked",
                tint = Color.White,
                modifier = Modifier
                    .size(checkboxSize * 0.7f)
                    .scale(checkIconScale)
            )
        }
    }
}
