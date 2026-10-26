package com.example.planora.ui.screens.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.window.Dialog
import com.example.planora.R
import com.example.planora.ui.components.AppTypography
import com.example.planora.ui.theme.EmeraldPrimary
import com.example.planora.ui.theme.IndigoPrimary
import com.example.planora.ui.theme.dimensions
import com.example.planora.util.canUseFullScreenIntent
import com.example.planora.util.openFullScreenIntentSettings
import com.example.planora.util.clickableDebounced

@Composable
fun NotificationStylePromptDialog(
    currentStyle: String,
    onStyleSelected: (String) -> Unit
) {
    val dims = dimensions

    Dialog(
        onDismissRequest = { onStyleSelected("NOTIFICATION") }
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(dims.spacing.default),
            shape = RoundedCornerShape(dims.corners.extraLarge),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            elevation = CardDefaults.cardElevation(defaultElevation = dims.elevations.medium)
        ) {
            Column(
                modifier = Modifier.padding(dims.spacing.large),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(dims.components.fab)
                        .clip(CircleShape)
                        .background(IndigoPrimary.copy(alpha = 0.12f))
                ) {
                    Icon(
                        imageVector = Icons.Default.NotificationsActive,
                        contentDescription = null,
                        tint = IndigoPrimary,
                        modifier = Modifier.size(dims.icons.large)
                    )
                }

                Spacer(modifier = Modifier.height(dims.spacing.default))

                AppTypography.TitleLarge(
                    text = stringResource(R.string.notif_prompt_title),
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(dims.spacing.small))

                AppTypography.BodyMedium(
                    text = stringResource(R.string.notif_prompt_message),
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(dims.spacing.large))

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
}
