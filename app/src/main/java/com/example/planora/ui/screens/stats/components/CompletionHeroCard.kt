package com.example.planora.ui.screens.stats.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.example.planora.R
import com.example.planora.ui.components.AppTypography
import com.example.planora.ui.theme.EmeraldPrimary
import com.example.planora.ui.theme.IndigoPrimary
import com.example.planora.ui.theme.dimensions

@Composable
fun CompletionHeroCard(
    completionRate: Float,
    modifier: Modifier = Modifier
) {
    val dims = dimensions
    val animatedProgress by animateFloatAsState(
        targetValue = (completionRate / 100f).coerceIn(0f, 1f),
        animationSpec = spring(stiffness = 200f),
        label = "statsProgress"
    )

    val shape = RoundedCornerShape(dims.corners.extraLarge)

    Card(
        modifier = modifier
            .fillMaxWidth()
            .clip(shape)
            .border(dims.borders.thin, IndigoPrimary.copy(alpha = 0.25f), shape),
        shape = shape,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = dims.elevations.low)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(dims.spacing.default),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                AppTypography.TitleMedium(
                    text = stringResource(R.string.stats_overall_completion),
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(dims.spacing.tiny))
                AppTypography.BodySmall(
                    text = if (completionRate >= 80f) {
                        stringResource(R.string.stats_msg_outstanding)
                    } else if (completionRate >= 50f) {
                        stringResource(R.string.stats_msg_good)
                    } else {
                        stringResource(R.string.stats_msg_stay_focused)
                    },
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(dims.spacing.medium))
                LinearProgressIndicator(
                    progress = { animatedProgress },
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .height(dims.spacing.small)
                        .clip(RoundedCornerShape(dims.corners.micro)),
                    color = EmeraldPrimary,
                    trackColor = MaterialTheme.colorScheme.surfaceVariant
                )
            }

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.size(dims.components.emptyStateIconContainer)
            ) {
                CircularProgressIndicator(
                    progress = { animatedProgress },
                    modifier = Modifier.fillMaxSize(),
                    strokeWidth = dims.spacing.small,
                    color = EmeraldPrimary,
                    trackColor = MaterialTheme.colorScheme.surfaceVariant
                )
                AppTypography.TitleMedium(
                    text = "${String.format("%.0f", completionRate)}%",
                    fontWeight = FontWeight.Black
                )
            }
        }
    }
}
