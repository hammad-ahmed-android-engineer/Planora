package com.example.planora.presentation.focus.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.example.planora.R
import com.example.planora.core.designsystem.theme.AppTypography
import com.example.planora.core.designsystem.theme.IndigoPrimary
import com.example.planora.core.designsystem.theme.dimensions
import com.example.planora.core.utils.rememberDebouncedClick

@Composable
fun TimerControlButtons(
    isRunning: Boolean,
    onPlayPause: () -> Unit,
    onReset: () -> Unit,
    modifier: Modifier = Modifier
) {
    val dims = dimensions
    val debouncedReset = rememberDebouncedClick(onClick = onReset)
    val debouncedPlayPause = rememberDebouncedClick(onClick = onPlayPause)

    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        IconButton(
            onClick = debouncedReset,
            modifier = Modifier
                .size(dims.components.fab)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
                .border(dims.borders.thin, MaterialTheme.colorScheme.outline.copy(alpha = 0.3f), CircleShape)
        ) {
            Icon(
                imageVector = Icons.Default.Refresh,
                contentDescription = stringResource(R.string.focus_reset),
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.size(dims.icons.medium)
            )
        }

        Spacer(modifier = Modifier.width(dims.spacing.default))

        Button(
            onClick = debouncedPlayPause,
            shape = RoundedCornerShape(dims.corners.extraLarge),
            colors = ButtonDefaults.buttonColors(
                containerColor = if (isRunning) Color(0xFFEF4444) else IndigoPrimary,
                contentColor = Color.White
            ),
            contentPadding = PaddingValues(horizontal = dims.spacing.default, vertical = dims.spacing.small),
            modifier = Modifier
                .height(dims.components.buttonHeight)
                .defaultMinSize(minWidth = dims.components.emptyStateIconContainer * 2.5f)
        ) {
            Icon(
                imageVector = if (isRunning) Icons.Default.Pause else Icons.Default.PlayArrow,
                contentDescription = null,
                modifier = Modifier.size(dims.icons.medium)
            )
            Spacer(modifier = Modifier.width(dims.spacing.small))
            AppTypography.TitleMedium(
                text = if (isRunning) stringResource(R.string.focus_pause) else stringResource(R.string.focus_start),
                fontWeight = FontWeight.Bold,
                color = Color.White,
                maxLines = 1
            )
        }
    }
}
