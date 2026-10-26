package com.example.planora.notification.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Alarm
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Snooze
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.example.planora.R
import com.example.planora.ui.components.AppTypography
import com.example.planora.ui.theme.BackgroundDark
import com.example.planora.ui.theme.EmeraldPrimary
import com.example.planora.ui.theme.IndigoPrimary
import com.example.planora.ui.theme.dimensions
import com.example.planora.util.rememberDebouncedClick
import java.time.LocalTime
import java.time.format.DateTimeFormatter

/**
 * Full-screen alert alarm UI displayed when scheduled task reminders fire.
 */
@Composable
fun FullScreenAlertContent(
    taskTitle: String,
    taskDesc: String,
    onMarkComplete: () -> Unit,
    onSnooze: () -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    val dims = dimensions
    val debouncedComplete = rememberDebouncedClick(onClick = onMarkComplete)
    val debouncedSnooze = rememberDebouncedClick(onClick = onSnooze)
    val debouncedDismiss = rememberDebouncedClick(onClick = onDismiss)

    val timeFormatted = remember {
        try {
            LocalTime.now().format(DateTimeFormatter.ofPattern("h:mm a"))
        } catch (_: Exception) {
            ""
        }
    }

    val infiniteTransition = rememberInfiniteTransition(label = "alertPulse")
    val pulseScale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.25f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulseScale"
    )
    val glowAlpha by infiniteTransition.animateFloat(
        initialValue = 0.3f,
        targetValue = 0.7f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "glowAlpha"
    )

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        BackgroundDark,
                        Color(0xFF0F172A),
                        Color(0xFF1E1B4B)
                    )
                )
            )
            .padding(dims.spacing.large),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            // Live Clock Time
            if (timeFormatted.isNotEmpty()) {
                AppTypography.TitleLarge(
                    text = timeFormatted,
                    fontWeight = FontWeight.Black,
                    color = Color.White.copy(alpha = 0.9f)
                )
                Spacer(modifier = Modifier.height(dims.spacing.medium))
            }

            // Pulsing Glowing Alarm Emblem
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.size(dims.components.emptyStateIconContainer * 2f)
            ) {
                Box(
                    modifier = Modifier
                        .size(dims.components.emptyStateIconContainer * 1.8f)
                        .scale(pulseScale)
                        .alpha(glowAlpha)
                        .clip(CircleShape)
                        .background(
                            Brush.radialGradient(
                                listOf(
                                    IndigoPrimary.copy(alpha = 0.5f),
                                    Color.Transparent
                                )
                            )
                        )
                )

                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(dims.components.emptyStateIconContainer)
                        .clip(CircleShape)
                        .background(IndigoPrimary.copy(alpha = 0.25f))
                        .border(dims.borders.medium, IndigoPrimary.copy(alpha = 0.7f), CircleShape)
                ) {
                    Icon(
                        imageVector = Icons.Default.Alarm,
                        contentDescription = "Alarm",
                        tint = Color.White,
                        modifier = Modifier.size(dims.icons.huge)
                    )
                }
            }

            Spacer(modifier = Modifier.height(dims.spacing.large))

            AppTypography.SubText(
                text = stringResource(R.string.task_reminder_title),
                color = IndigoPrimary,
                fontSize = dims.textSizes.small
            )

            Spacer(modifier = Modifier.height(dims.spacing.small))

            AppTypography.TitleLarge(
                text = taskTitle,
                fontWeight = FontWeight.ExtraBold,
                color = Color.White,
                textAlign = TextAlign.Center
            )

            if (taskDesc.isNotBlank()) {
                Spacer(modifier = Modifier.height(dims.spacing.medium))
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(dims.corners.large))
                        .border(dims.borders.thin, Color.White.copy(alpha = 0.15f), RoundedCornerShape(dims.corners.large)),
                    shape = RoundedCornerShape(dims.corners.large),
                    colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.08f))
                ) {
                    AppTypography.BodyMedium(
                        text = taskDesc,
                        color = Color(0xFFCBD5E1),
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .padding(dims.spacing.default)
                            .fillMaxWidth()
                    )
                }
            }

            Spacer(modifier = Modifier.height(dims.spacing.extraLarge))

            Button(
                onClick = debouncedComplete,
                shape = RoundedCornerShape(dims.corners.pill),
                colors = ButtonDefaults.buttonColors(
                    containerColor = EmeraldPrimary,
                    contentColor = Color.White
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(dims.components.buttonHeight)
            ) {
                Icon(imageVector = Icons.Default.CheckCircle, contentDescription = null, modifier = Modifier.size(dims.icons.medium))
                Spacer(modifier = Modifier.width(dims.spacing.small))
                AppTypography.TitleMedium(
                    text = stringResource(R.string.mark_complete),
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.height(dims.spacing.medium))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(dims.spacing.medium)
            ) {
                OutlinedButton(
                    onClick = debouncedSnooze,
                    shape = RoundedCornerShape(dims.corners.large),
                    modifier = Modifier
                        .weight(1f)
                        .height(dims.components.buttonHeight)
                ) {
                    Icon(imageVector = Icons.Default.Snooze, contentDescription = null, tint = Color.White, modifier = Modifier.size(dims.icons.small))
                    Spacer(modifier = Modifier.width(dims.spacing.tiny))
                    AppTypography.BodyMedium(
                        text = stringResource(R.string.snooze_15m),
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White
                    )
                }

                OutlinedButton(
                    onClick = debouncedDismiss,
                    shape = RoundedCornerShape(dims.corners.large),
                    modifier = Modifier
                        .weight(1f)
                        .height(dims.components.buttonHeight)
                ) {
                    Icon(imageVector = Icons.Default.Close, contentDescription = null, tint = Color(0xFF94A3B8), modifier = Modifier.size(dims.icons.small))
                    Spacer(modifier = Modifier.width(dims.spacing.tiny))
                    AppTypography.BodyMedium(
                        text = stringResource(R.string.dismiss),
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFF94A3B8)
                    )
                }
            }
        }
    }
}
