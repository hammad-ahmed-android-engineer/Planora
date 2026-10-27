package com.example.planora.presentation.focus.components

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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.example.planora.R
import com.example.planora.core.designsystem.components.AppCard
import com.example.planora.core.designsystem.theme.AppTypography
import com.example.planora.core.designsystem.theme.FlameOrange
import com.example.planora.core.designsystem.theme.IndigoPrimary
import com.example.planora.core.designsystem.theme.dimensions

@Composable
fun FocusStatsPill(
    minsToday: Int,
    sessionsToday: Int,
    modifier: Modifier = Modifier
) {
    val dims = dimensions
    AppCard(
        modifier = modifier.fillMaxWidth(),
        containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f),
        borderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.25f)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(dims.spacing.medium),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Timer,
                    contentDescription = null,
                    tint = IndigoPrimary,
                    modifier = Modifier.size(dims.icons.small)
                )
                Spacer(modifier = Modifier.width(dims.spacing.tiny))
                Column {
                    AppTypography.TitleSmall(
                        text = stringResource(R.string.focus_mins_today, minsToday),
                        fontWeight = FontWeight.Bold
                    )
                    AppTypography.Caption(
                        text = stringResource(R.string.focus_focused_today),
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            Box(
                modifier = Modifier
                    .width(dims.borders.thin)
                    .height(dims.components.buttonSmallHeight)
                    .background(MaterialTheme.colorScheme.outline.copy(alpha = 0.3f))
            )

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.LocalFireDepartment,
                    contentDescription = null,
                    tint = FlameOrange,
                    modifier = Modifier.size(dims.icons.small)
                )
                Spacer(modifier = Modifier.width(dims.spacing.tiny))
                Column {
                    AppTypography.TitleSmall(
                        text = stringResource(R.string.focus_sessions_today, sessionsToday),
                        fontWeight = FontWeight.Bold
                    )
                    AppTypography.Caption(
                        text = stringResource(R.string.focus_completed_label),
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}
