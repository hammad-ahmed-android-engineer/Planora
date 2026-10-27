package com.example.planora.presentation.stats.components

import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.example.planora.R
import com.example.planora.core.designsystem.components.AppCard
import com.example.planora.core.designsystem.components.AppLinearProgressIndicator
import com.example.planora.core.designsystem.theme.AppTypography
import com.example.planora.core.designsystem.theme.EmeraldPrimary
import com.example.planora.core.designsystem.theme.FlameOrange
import com.example.planora.core.designsystem.theme.IndigoPrimary
import com.example.planora.core.designsystem.theme.TealPrimary
import com.example.planora.core.designsystem.theme.VioletPrimary
import com.example.planora.core.designsystem.theme.dimensions

@Composable
fun CategoryDistributionCard(
    categories: Map<String, Int>,
    modifier: Modifier = Modifier
) {
    val dims = dimensions
    val totalTasks = categories.values.sum().coerceAtLeast(1)

    AppCard(
        modifier = modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(dims.spacing.default)) {
            AppTypography.TitleMedium(
                text = stringResource(R.string.stats_category_distribution),
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(dims.spacing.medium))

            if (categories.isEmpty()) {
                AppTypography.BodySmall(
                    text = stringResource(R.string.stats_no_category_data),
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            } else {
                val palette = listOf(IndigoPrimary, EmeraldPrimary, FlameOrange, VioletPrimary, TealPrimary)
                categories.entries.toList().forEachIndexed { index, (catName, count) ->
                    val color = palette[index % palette.size]
                    val fraction = count.toFloat() / totalTasks.toFloat()

                    Column(modifier = Modifier.padding(vertical = dims.spacing.tiny)) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(
                                    modifier = Modifier
                                        .size(dims.icons.micro)
                                        .clip(CircleShape)
                                        .background(color)
                                )
                                Spacer(modifier = Modifier.width(dims.spacing.tiny))
                                AppTypography.BodyMedium(
                                    text = catName,
                                    fontWeight = FontWeight.Medium
                                )
                            }
                            AppTypography.Caption(
                                text = "$count (${(fraction * 100).toInt()}%)",
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                        Spacer(modifier = Modifier.height(dims.spacing.tiny))
                        AppLinearProgressIndicator(
                            progress = fraction,
                            color = color,
                            height = dims.spacing.micro
                        )
                    }
                }
            }
        }
    }
}
