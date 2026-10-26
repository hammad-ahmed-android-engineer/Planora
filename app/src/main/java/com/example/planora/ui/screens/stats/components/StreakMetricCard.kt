package com.example.planora.ui.screens.stats.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.example.planora.R
import com.example.planora.ui.components.AppTypography
import com.example.planora.ui.theme.FlameOrange
import com.example.planora.ui.theme.IndigoPrimary
import com.example.planora.ui.theme.dimensions

@Composable
fun StreakMetricCard(
    title: String,
    days: Int,
    isCurrent: Boolean,
    modifier: Modifier = Modifier
) {
    val dims = dimensions
    val infiniteTransition = rememberInfiniteTransition(label = "streakPulse")
    val flameScale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = if (isCurrent && days > 0) 1.15f else 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(900, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "flameScale"
    )

    val shape = RoundedCornerShape(dims.corners.large)

    Card(
        modifier = modifier
            .clip(shape)
            .border(
                dims.borders.thin,
                if (isCurrent && days > 0) FlameOrange.copy(alpha = 0.35f) else MaterialTheme.colorScheme.outline.copy(alpha = 0.25f),
                shape
            ),
        shape = shape,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = dims.elevations.low)
    ) {
        Column(modifier = Modifier.padding(dims.spacing.medium)) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(dims.components.buttonSmallHeight)
                    .clip(CircleShape)
                    .background(
                        if (isCurrent && days > 0) FlameOrange.copy(alpha = 0.15f) else IndigoPrimary.copy(alpha = 0.12f)
                    )
            ) {
                Icon(
                    imageVector = Icons.Default.LocalFireDepartment,
                    contentDescription = null,
                    tint = if (isCurrent && days > 0) FlameOrange else IndigoPrimary,
                    modifier = Modifier
                        .size(dims.icons.medium)
                        .scale(flameScale)
                )
            }
            Spacer(modifier = Modifier.height(dims.spacing.small))
            AppTypography.TitleLarge(
                text = if (days == 1) stringResource(R.string.stats_days_one) else stringResource(R.string.stats_days_count, days),
                fontWeight = FontWeight.ExtraBold
            )
            AppTypography.Caption(
                text = title,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
