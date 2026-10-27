package com.example.planora.core.designsystem.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import com.example.planora.core.designsystem.theme.dimensions
import com.example.planora.core.utils.clickableDebounced

@Composable
fun AppCard(
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
    containerColor: Color = MaterialTheme.colorScheme.surface,
    borderColor: Color = MaterialTheme.colorScheme.outline.copy(alpha = 0.35f),
    content: @Composable ColumnScope.() -> Unit
) {
    val dims = dimensions
    val shape = RoundedCornerShape(dims.corners.large)

    val clickableModifier = if (onClick != null) {
        Modifier
            .clip(shape)
            .clickableDebounced(onClick = onClick)
    } else {
        Modifier.clip(shape)
    }

    Card(
        modifier = modifier
            .then(clickableModifier)
            .border(dims.borders.thin, borderColor, shape),
        shape = shape,
        colors = CardDefaults.cardColors(containerColor = containerColor),
        elevation = CardDefaults.cardElevation(defaultElevation = dims.elevations.low)
    ) {
        Column {
            content()
        }
    }
}
