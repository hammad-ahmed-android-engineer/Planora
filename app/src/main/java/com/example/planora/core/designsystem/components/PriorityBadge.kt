package com.example.planora.core.designsystem.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Flag
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import com.example.planora.core.designsystem.theme.AppTypography
import com.example.planora.core.designsystem.theme.PriorityHigh
import com.example.planora.core.designsystem.theme.PriorityLow
import com.example.planora.core.designsystem.theme.PriorityMedium
import com.example.planora.core.designsystem.theme.PriorityNone
import com.example.planora.core.designsystem.theme.PriorityUrgent
import com.example.planora.core.designsystem.theme.dimensions
import com.example.planora.domain.model.Priority

@Composable
fun PriorityBadge(priority: Priority, modifier: Modifier = Modifier) {
    if (priority == Priority.NONE) return

    val dims = dimensions
    val color = when (priority) {
        Priority.LOW -> PriorityLow
        Priority.MEDIUM -> PriorityMedium
        Priority.HIGH -> PriorityHigh
        Priority.URGENT -> PriorityUrgent
        Priority.NONE -> PriorityNone
    }

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(dims.corners.small))
            .background(color.copy(alpha = 0.14f))
            .padding(horizontal = dims.spacing.small, vertical = dims.spacing.micro)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = Icons.Default.Flag,
                contentDescription = null,
                tint = color,
                modifier = Modifier.size(dims.icons.micro)
            )
            Spacer(modifier = Modifier.width(dims.spacing.micro))
            AppTypography.SubText(
                text = priority.label,
                color = color,
                fontSize = dims.textSizes.caption
            )
        }
    }
}
