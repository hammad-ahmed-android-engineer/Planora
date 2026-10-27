package com.example.planora.presentation.categories.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DeleteOutline
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.example.planora.R
import com.example.planora.core.designsystem.components.AppCard
import com.example.planora.core.designsystem.components.AppIconButton
import com.example.planora.core.designsystem.theme.AppTypography
import com.example.planora.core.designsystem.theme.dimensions
import com.example.planora.core.utils.parseHexColor
import com.example.planora.core.utils.rememberDebouncedClick
import com.example.planora.domain.model.Category

@Composable
fun CategoryCardItem(
    category: Category,
    onDelete: () -> Unit,
    modifier: Modifier = Modifier
) {
    val dims = dimensions
    val color = category.colorHex.parseHexColor()
    val debouncedDelete = rememberDebouncedClick(onClick = onDelete)

    AppCard(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = dims.spacing.tiny)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(dims.spacing.medium)
        ) {
            Box(
                modifier = Modifier
                    .size(dims.components.colorPickerCircle)
                    .clip(CircleShape)
                    .background(color.copy(alpha = 0.16f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Folder,
                    contentDescription = null,
                    tint = color,
                    modifier = Modifier.size(dims.icons.medium)
                )
            }
            Spacer(modifier = Modifier.width(dims.spacing.medium))
            Column(modifier = Modifier.weight(1f)) {
                AppTypography.TitleMedium(
                    text = category.name,
                    fontWeight = FontWeight.SemiBold
                )
                if (category.description.isNotBlank()) {
                    AppTypography.BodySmall(
                        text = category.description,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
            AppIconButton(
                onClick = debouncedDelete,
                icon = Icons.Default.DeleteOutline,
                contentDescription = stringResource(R.string.delete),
                tint = MaterialTheme.colorScheme.error.copy(alpha = 0.7f),
                iconSize = dims.icons.medium
            )
        }
    }
}
