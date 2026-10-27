package com.example.planora.presentation.home.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.example.planora.R
import com.example.planora.core.designsystem.theme.AppTypography
import com.example.planora.core.designsystem.theme.FlameOrange
import com.example.planora.core.designsystem.theme.FlameYellow
import com.example.planora.core.designsystem.theme.dimensions

@Composable
fun GreetingHeroHeader(
    greeting: String,
    dateStr: String,
    streak: Int,
    modifier: Modifier = Modifier
) {
    val dims = dimensions
    val infiniteTransition = rememberInfiniteTransition(label = "flameGlow")
    val flameScale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.14f,
        animationSpec = infiniteRepeatable(
            animation = tween(900, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "flameScale"
    )

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier.fillMaxWidth()
    ) {
        Column {
            AppTypography.TitleLarge(
                text = greeting,
                fontWeight = FontWeight.ExtraBold
            )
            Spacer(modifier = Modifier.height(dims.spacing.micro))
            AppTypography.BodyMedium(
                text = dateStr,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        if (streak > 0) {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(dims.corners.large))
                    .background(
                        Brush.horizontalGradient(
                            listOf(
                                FlameOrange.copy(alpha = 0.18f),
                                FlameYellow.copy(alpha = 0.22f)
                            )
                        )
                    )
                    .border(
                        dims.borders.thin,
                        FlameOrange.copy(alpha = 0.4f),
                        RoundedCornerShape(dims.corners.large)
                    )
                    .padding(horizontal = dims.spacing.medium, vertical = dims.spacing.small)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.LocalFireDepartment,
                        contentDescription = null,
                        tint = FlameOrange,
                        modifier = Modifier
                            .size(dims.icons.medium)
                            .scale(flameScale)
                    )
                    Spacer(modifier = Modifier.width(dims.spacing.micro))
                    AppTypography.SubText(
                        text = stringResource(R.string.home_streak_format, streak),
                        color = FlameOrange
                    )
                }
            }
        }
    }
}
