package com.example.planora.presentation.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import com.example.planora.core.designsystem.theme.AppTypography
import com.example.planora.core.designsystem.theme.dimensions

@Composable
fun StatPillCard(
    label: String,
    value: String,
    icon: ImageVector,
    color: Color,
    modifier: Modifier = Modifier
) {
    val dims = dimensions
    val shape = RoundedCornerShape(dims.corners.medium)
    Box(
        modifier = modifier
            .clip(shape)
            .background(color.copy(alpha = 0.12f))
            .border(dims.borders.thin, color.copy(alpha = 0.25f), shape)
            .padding(dims.spacing.medium)
    ) {
        Column(horizontalAlignment = Alignment.Start) {
            Icon(imageVector = icon, contentDescription = null, tint = color, modifier = Modifier.size(dims.icons.small))
            Spacer(modifier = Modifier.height(dims.spacing.micro))
            AppTypography.TitleMedium(text = value, fontWeight = FontWeight.Bold, color = color)
            AppTypography.Caption(text = label, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
}
