package com.example.planora.presentation.settings.components

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
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.SettingsBrightness
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.example.planora.R
import com.example.planora.core.designsystem.components.AppCard
import com.example.planora.core.designsystem.theme.AppTypography
import com.example.planora.core.designsystem.theme.dimensions
import com.example.planora.core.utils.clickableDebounced
import com.example.planora.core.utils.parseHexColor

@Composable
fun AppearanceSettingsCard(
    themeMode: String,
    accentColorHex: String,
    accentColors: List<String>,
    onThemeModeChanged: (String) -> Unit,
    onAccentColorChanged: (String) -> Unit
) {
    val dims = dimensions
    val primaryColor = MaterialTheme.colorScheme.primary

    AppCard(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(dims.spacing.default)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Palette,
                    contentDescription = null,
                    tint = primaryColor,
                    modifier = Modifier.size(dims.icons.medium)
                )
                Spacer(modifier = Modifier.width(dims.spacing.small))
                AppTypography.TitleMedium(
                    text = stringResource(R.string.settings_appearance_header),
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(dims.spacing.medium))

            AppTypography.BodySmall(
                text = stringResource(R.string.settings_theme_mode),
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
                    Triple("LIGHT", "Light", Icons.Default.WbSunny),
                    Triple("DARK", "Dark", Icons.Default.DarkMode),
                    Triple("SYSTEM", "System", Icons.Default.SettingsBrightness)
                ).forEach { (modeKey, label, icon) ->
                    val isSelected = themeMode.equals(modeKey, ignoreCase = true)
                    val bgColor by animateColorAsState(
                        targetValue = if (isSelected) primaryColor else Color.Transparent,
                        animationSpec = tween(200),
                        label = "themeBg"
                    )
                    val textColor by animateColorAsState(
                        targetValue = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurfaceVariant,
                        animationSpec = tween(200),
                        label = "themeText"
                    )

                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(dims.corners.small))
                            .background(bgColor)
                            .clickableDebounced { onThemeModeChanged(modeKey) }
                            .padding(vertical = dims.spacing.small),
                        contentAlignment = Alignment.Center
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = icon,
                                contentDescription = null,
                                tint = textColor,
                                modifier = Modifier.size(dims.icons.small)
                            )
                            Spacer(modifier = Modifier.width(dims.spacing.micro))
                            AppTypography.SubText(
                                text = label,
                                color = textColor
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(dims.spacing.default))

            AppTypography.BodySmall(
                text = stringResource(R.string.settings_accent_color),
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(dims.spacing.small))

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                accentColors.forEach { hex ->
                    val color = hex.parseHexColor()
                    val isSelected = accentColorHex.equals(hex, ignoreCase = true)

                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .size(dims.components.colorPickerCircle)
                            .clip(CircleShape)
                            .background(color)
                            .clickableDebounced { onAccentColorChanged(hex) }
                            .then(
                                if (isSelected) {
                                    Modifier.border(dims.borders.thick, Color.White, CircleShape)
                                } else {
                                    Modifier
                                }
                            )
                    ) {
                        if (isSelected) {
                            Icon(
                                imageVector = Icons.Default.Check,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(dims.icons.medium)
                            )
                        }
                    }
                }
            }
        }
    }
}
