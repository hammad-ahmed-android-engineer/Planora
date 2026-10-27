package com.example.planora.presentation.splash.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import com.example.planora.core.designsystem.theme.AppTypography
import com.example.planora.core.designsystem.theme.EmeraldPrimary
import com.example.planora.core.designsystem.theme.IndigoPrimary
import com.example.planora.core.designsystem.theme.VioletPrimary
import com.example.planora.core.designsystem.theme.dimensions

@Composable
fun SplashBranding(
    textAlpha: Animatable<Float, AnimationVector1D>,
    modifier: Modifier = Modifier
) {
    val dims = dimensions
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        AppTypography.TitleLarge(
            text = "PLANORA",
            fontWeight = FontWeight.Black,
            color = Color.White,
            modifier = Modifier.alpha(textAlpha.value)
        )

        Spacer(modifier = Modifier.height(dims.spacing.small))

        AppTypography.BodyMedium(
            text = "Plan with clarity. Achieve with ease.",
            color = Color(0xFF94A3B8),
            fontWeight = FontWeight.Medium,
            modifier = Modifier.alpha(textAlpha.value)
        )

        Spacer(modifier = Modifier.height(dims.components.buttonHeight))

        Box(
            modifier = Modifier
                .width(dims.components.buttonHeight)
                .height(dims.borders.thick)
                .alpha(textAlpha.value)
                .clip(RoundedCornerShape(dims.corners.micro))
                .background(
                    Brush.horizontalGradient(
                        listOf(
                            IndigoPrimary,
                            EmeraldPrimary,
                            VioletPrimary
                        )
                    )
                )
        )
    }
}
