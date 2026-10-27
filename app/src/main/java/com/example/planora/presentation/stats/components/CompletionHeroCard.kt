package com.example.planora.presentation.stats.components

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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.example.planora.R
import com.example.planora.core.designsystem.components.AppCard
import com.example.planora.core.designsystem.components.AppLinearProgressIndicator
import com.example.planora.core.designsystem.theme.AppTypography
import com.example.planora.core.designsystem.theme.EmeraldPrimary
import com.example.planora.core.designsystem.theme.IndigoPrimary
import com.example.planora.core.designsystem.theme.dimensions

@Composable
fun CompletionHeroCard(
    completionRate: Float,
    modifier: Modifier = Modifier
) {
    val dims = dimensions
    val progressFraction = (completionRate / 100f).coerceIn(0f, 1f)

    AppCard(
        modifier = modifier.fillMaxWidth(),
        borderColor = IndigoPrimary.copy(alpha = 0.25f)
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
                AppLinearProgressIndicator(
                    progress = progressFraction,
                    color = EmeraldPrimary,
                    height = dims.spacing.small,
                    modifier = Modifier.fillMaxWidth(0.9f)
                )
            }

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.size(dims.components.emptyStateIconContainer)
            ) {
                CircularProgressIndicator(
                    progress = { progressFraction },
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
