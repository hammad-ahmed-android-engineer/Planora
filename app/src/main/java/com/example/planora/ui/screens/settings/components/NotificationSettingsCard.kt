package com.example.planora.ui.screens.settings.components

import androidx.compose.animation.animateColorAsState
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.example.planora.R
import com.example.planora.ui.components.AppTypography
import com.example.planora.ui.theme.dimensions
import com.example.planora.util.canUseFullScreenIntent
import com.example.planora.util.openFullScreenIntentSettings
import com.example.planora.util.clickableDebounced

@Composable
fun NotificationSettingsCard(
    notificationsEnabled: Boolean,
    notificationStyle: String,
    onToggle: (Boolean) -> Unit,
    onNotificationStyleChanged: (String) -> Unit
) {
    val dims = MaterialTheme.dimensions
    val primaryColor = MaterialTheme.colorScheme.primary
    val shape = RoundedCornerShape(dims.corners.extraLarge)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(shape)
            .border(dims.borders.thin, MaterialTheme.colorScheme.outline.copy(alpha = 0.25f), shape),
        shape = shape,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = dims.elevations.low)
    ) {
        Column(modifier = Modifier.padding(dims.spacing.default)) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(dims.components.colorPickerCircle)
                        .clip(CircleShape)
                        .background(primaryColor.copy(alpha = 0.12f))
                ) {
                    Icon(
                        imageVector = Icons.Default.Notifications,
                        contentDescription = null,
                        tint = primaryColor,
                        modifier = Modifier.size(dims.icons.medium)
                    )
                }

                Spacer(modifier = Modifier.width(dims.spacing.medium))

                Column(modifier = Modifier.weight(1f)) {
                    AppTypography.TitleMedium(
                        text = stringResource(R.string.settings_task_reminders),
                        fontWeight = FontWeight.SemiBold
                    )
                    AppTypography.BodySmall(
                        text = stringResource(R.string.settings_task_reminders_desc),
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                Spacer(modifier = Modifier.width(dims.spacing.medium))

                Switch(
                    checked = notificationsEnabled,
                    onCheckedChange = onToggle,
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = Color.White,
                        checkedTrackColor = primaryColor
                    )
                )
            }

            if (notificationsEnabled) {
                Spacer(modifier = Modifier.height(dims.spacing.default))

                AppTypography.BodySmall(
                    text = stringResource(R.string.settings_notif_style),
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.height(dims.spacing.small))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(dims.corners.medium))
                        .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
                        .border(dims.borders.thin, MaterialTheme.colorScheme.outline.copy(alpha = 0.25f), RoundedCornerShape(dims.corners.medium))
                        .padding(dims.spacing.micro),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    listOf(
                        Pair("NOTIFICATION", stringResource(R.string.notif_option_bar_title_short)),
                        Pair("FULL_SCREEN", stringResource(R.string.notif_option_fullscreen_title_short))
                    ).forEach { (styleKey, label) ->
                        val isSelected = notificationStyle.equals(styleKey, ignoreCase = true)
                        val bgColor by animateColorAsState(
                            targetValue = if (isSelected) primaryColor else Color.Transparent,
                            animationSpec = tween(200),
                            label = "styleBg"
                        )
                        val textColor by animateColorAsState(
                            targetValue = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurfaceVariant,
                            animationSpec = tween(200),
                            label = "styleText"
                        )

                        val context = LocalContext.current
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .height(dims.components.buttonHeight)
                                .clip(RoundedCornerShape(dims.corners.medium))
                                .background(bgColor)
                                .clickableDebounced {
                                    onNotificationStyleChanged(styleKey)
                                    if (styleKey == "FULL_SCREEN" && !context.canUseFullScreenIntent()) {
                                        context.openFullScreenIntentSettings()
                                    }
                                }
                                .padding(horizontal = dims.spacing.medium),
                            contentAlignment = Alignment.Center
                        ) {
                            AppTypography.BodyMedium(
                                text = label,
                                color = textColor,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                textAlign = TextAlign.Center,
                                maxLines = 1
                            )
                        }
                    }
                }
            }
        }
    }
}
