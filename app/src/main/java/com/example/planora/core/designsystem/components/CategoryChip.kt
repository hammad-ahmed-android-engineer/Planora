package com.example.planora.core.designsystem.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import com.example.planora.core.designsystem.theme.AppTypography
import com.example.planora.core.designsystem.theme.dimensions
import com.example.planora.core.designsystem.theme.parseColorHex
import com.example.planora.domain.model.Category

@Composable
fun CategoryChip(category: Category, modifier: Modifier = Modifier) {
    val dims = dimensions
    val color = parseColorHex(category.colorHex)

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .clip(RoundedCornerShape(dims.corners.small))
            .background(color.copy(alpha = 0.14f))
            .padding(horizontal = dims.spacing.small, vertical = dims.spacing.micro)
    ) {
        Box(
            modifier = Modifier
                .size(dims.icons.tiny)
                .clip(CircleShape)
                .background(color)
        )
        Spacer(modifier = Modifier.width(dims.spacing.micro))
        AppTypography.SubText(
            text = category.name,
            color = color,
            fontSize = dims.textSizes.caption
        )
    }
}
