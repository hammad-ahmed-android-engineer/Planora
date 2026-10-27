package com.example.planora.core.designsystem.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import com.example.planora.core.designsystem.theme.AppTypography
import com.example.planora.core.designsystem.theme.dimensions
import com.example.planora.core.utils.rememberDebouncedClick

@Composable
fun AppButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    shape: Shape = RoundedCornerShape(dimensions.corners.medium),
    containerColor: Color = MaterialTheme.colorScheme.primary,
    contentColor: Color = Color.White,
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    content: @Composable RowScope.() -> Unit
) {
    val debouncedClick = rememberDebouncedClick(onClick = onClick)
    Button(
        onClick = debouncedClick,
        enabled = enabled,
        modifier = modifier,
        shape = shape,
        contentPadding = contentPadding,
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            contentColor = contentColor
        ),
        content = content
    )
}

@Composable
fun AppButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    icon: ImageVector? = null,
    enabled: Boolean = true,
    shape: Shape = RoundedCornerShape(dimensions.corners.medium),
    containerColor: Color = MaterialTheme.colorScheme.primary,
    contentColor: Color = Color.White
) {
    val dims = dimensions
    val debouncedClick = rememberDebouncedClick(onClick = onClick)
    Button(
        onClick = debouncedClick,
        enabled = enabled,
        modifier = modifier,
        shape = shape,
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            contentColor = contentColor
        )
    ) {
        if (icon != null) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(dims.icons.small)
            )
            Spacer(modifier = Modifier.width(dims.spacing.small))
        }
        AppTypography.BodyMedium(
            text = text,
            color = contentColor,
            fontWeight = FontWeight.Bold
        )
    }
}
