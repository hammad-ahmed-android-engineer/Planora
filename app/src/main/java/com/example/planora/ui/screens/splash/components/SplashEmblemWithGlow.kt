package com.example.planora.ui.screens.splash.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import com.example.planora.R
import com.example.planora.ui.theme.IndigoPrimary
import com.example.planora.ui.theme.VioletPrimary
import com.example.planora.ui.theme.dimensions

@Composable
fun SplashEmblemWithGlow(
    scale: Animatable<Float, AnimationVector1D>,
    contentAlpha: Animatable<Float, AnimationVector1D>,
    pulseScale: Float,
    glowAlpha: Float,
    modifier: Modifier = Modifier
) {
    val dims = dimensions
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
    ) {
        // Ambient radial glowing halo
        Box(
            modifier = Modifier
                .size(dims.components.emptyStateIconContainer * 3)
                .scale(pulseScale)
                .alpha(glowAlpha)
                .background(
                    Brush.radialGradient(
                        colors = listOf(
                            IndigoPrimary.copy(alpha = 0.5f),
                            VioletPrimary.copy(alpha = 0.25f),
                            Color.Transparent
                        )
                    ),
                    shape = CircleShape
                )
        )

        // Animated Logo Emblem
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .scale(scale.value)
                .alpha(contentAlpha.value)
                .size(dims.components.emptyStateIconContainer * 1.8f)
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_splash_logo),
                contentDescription = "Planora Logo",
                modifier = Modifier.size(dims.components.emptyStateIconContainer * 1.6f)
            )
        }
    }
}
