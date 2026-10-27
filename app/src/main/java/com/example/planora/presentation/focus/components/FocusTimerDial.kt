package com.example.planora.presentation.focus.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import com.example.planora.presentation.focus.FocusMode
import com.example.planora.core.designsystem.theme.AppTypography
import com.example.planora.core.designsystem.theme.EmeraldPrimary
import com.example.planora.core.designsystem.theme.IndigoPrimary
import com.example.planora.core.designsystem.theme.dimensions
import com.example.planora.core.utils.formatSecondsToMinSec

@Composable
fun FocusTimerDial(
    remainingSeconds: Int,
    totalSeconds: Int,
    mode: FocusMode,
    isRunning: Boolean,
    size: Dp,
    modifier: Modifier = Modifier
) {
    val dims = dimensions
    val timeFormatted = remainingSeconds.formatSecondsToMinSec()
    val progress = if (totalSeconds > 0) remainingSeconds.toFloat() / totalSeconds.toFloat() else 0f

    val animatedProgress by animateFloatAsState(
        targetValue = progress,
        animationSpec = spring(stiffness = 300f),
        label = "timerProgress"
    )

    val infiniteTransition = rememberInfiniteTransition(label = "timerPulse")
    val breathingScale by infiniteTransition.animateFloat(
        initialValue = 1.0f,
        targetValue = if (isRunning) 1.15f else 1.0f,
        animationSpec = infiniteRepeatable(
            animation = tween(1200, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "breathingScale"
    )
    val glowAlpha by infiniteTransition.animateFloat(
        initialValue = if (isRunning) 0.2f else 0.0f,
        targetValue = if (isRunning) 0.5f else 0.0f,
        animationSpec = infiniteRepeatable(
            animation = tween(1200, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "glowAlpha"
    )

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.size(size)
    ) {
        if (isRunning) {
            Box(
                modifier = Modifier
                    .size(size)
                    .scale(breathingScale)
                    .alpha(glowAlpha)
                    .background(
                        Brush.radialGradient(
                            listOf(
                                IndigoPrimary.copy(alpha = 0.5f),
                                Color.Transparent
                            )
                        ),
                        shape = CircleShape
                    )
            )
        }

        CircularProgressIndicator(
            progress = { animatedProgress },
            modifier = Modifier.fillMaxSize(),
            strokeWidth = dims.spacing.medium,
            color = if (mode == FocusMode.FOCUS) IndigoPrimary else EmeraldPrimary,
            trackColor = MaterialTheme.colorScheme.surfaceVariant
        )

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            AppTypography.TitleLarge(
                text = timeFormatted,
                fontWeight = FontWeight.Black
            )
            Spacer(modifier = Modifier.height(dims.spacing.micro))
            AppTypography.SubText(
                text = mode.label,
                color = IndigoPrimary
            )
        }
    }
}
