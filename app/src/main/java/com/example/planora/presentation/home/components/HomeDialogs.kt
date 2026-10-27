package com.example.planora.presentation.home.components

import androidx.compose.foundation.border
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
import androidx.compose.material.icons.filled.Alarm
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.NotificationsActive
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.example.planora.R
import com.example.planora.core.designsystem.components.AppDialog
import com.example.planora.core.designsystem.theme.AppTypography
import com.example.planora.core.designsystem.theme.EmeraldPrimary
import com.example.planora.core.designsystem.theme.IndigoPrimary
import com.example.planora.core.designsystem.theme.dimensions
import com.example.planora.core.utils.canUseFullScreenIntent
import com.example.planora.core.utils.clickableDebounced
import com.example.planora.core.utils.openFullScreenIntentSettings

@Composable
fun NotificationStylePromptDialog(
    currentStyle: String,
    onStyleSelected: (String) -> Unit
) {
    val dims = dimensions

    AppDialog(
        onDismissRequest = { onStyleSelected("NOTIFICATION") },
        icon = Icons.Default.NotificationsActive,
        iconTint = IndigoPrimary,
        title = stringResource(R.string.notif_prompt_title),
        subtitle = stringResource(R.string.notif_prompt_message)
    ) {
        Column {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(dims.corners.large))
                    .border(dims.borders.thin, IndigoPrimary.copy(alpha = 0.3f), RoundedCornerShape(dims.corners.large))
                    .clickableDebounced { onStyleSelected("NOTIFICATION") },
                shape = RoundedCornerShape(dims.corners.large),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f))
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(dims.spacing.default)
                ) {
                    Icon(
                        imageVector = Icons.Default.Notifications,
                        contentDescription = null,
                        tint = IndigoPrimary,
                        modifier = Modifier.size(dims.icons.medium)
                    )
                    Spacer(modifier = Modifier.width(dims.spacing.medium))
                    Column(modifier = Modifier.weight(1f)) {
                        AppTypography.TitleSmall(text = stringResource(R.string.notif_option_bar_title))
                        AppTypography.BodySmall(
                            text = stringResource(R.string.notif_option_bar_desc),
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(dims.spacing.medium))

            val context = LocalContext.current
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(dims.corners.large))
                    .border(dims.borders.thin, EmeraldPrimary.copy(alpha = 0.3f), RoundedCornerShape(dims.corners.large))
                    .clickableDebounced {
                        onStyleSelected("FULL_SCREEN")
                        if (!context.canUseFullScreenIntent()) {
                            context.openFullScreenIntentSettings()
                        }
                    },
                shape = RoundedCornerShape(dims.corners.large),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f))
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(dims.spacing.default)
                ) {
                    Icon(
                        imageVector = Icons.Default.Alarm,
                        contentDescription = null,
                        tint = EmeraldPrimary,
                        modifier = Modifier.size(dims.icons.medium)
                    )
                    Spacer(modifier = Modifier.width(dims.spacing.medium))
                    Column(modifier = Modifier.weight(1f)) {
                        AppTypography.TitleSmall(text = stringResource(R.string.notif_option_fullscreen_title))
                        AppTypography.BodySmall(
                            text = stringResource(R.string.notif_option_fullscreen_desc),
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(dims.spacing.default))

            AppTypography.Caption(
                text = stringResource(R.string.notif_prompt_footer),
                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
            )
        }
    }
}
