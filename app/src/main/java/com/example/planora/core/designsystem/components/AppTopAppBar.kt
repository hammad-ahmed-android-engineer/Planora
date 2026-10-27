package com.example.planora.core.designsystem.components

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.example.planora.R
import com.example.planora.core.designsystem.theme.AppTypography
import com.example.planora.core.utils.rememberDebouncedClick

/**
 * Standardized Top App Bar component with customizable title, subtitle, navigation icon,
 * debounced click navigation, actions, and theme colors.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopAppBar(
    title: String,
    modifier: Modifier = Modifier,
    subtitle: String? = null,
    onNavigateBack: (() -> Unit)? = null,
    actions: @Composable (RowScope.() -> Unit) = {},
    containerColor: Color = MaterialTheme.colorScheme.background,
    titleColor: Color = MaterialTheme.colorScheme.onBackground,
    navigationIconColor: Color = MaterialTheme.colorScheme.primary,
    colors: TopAppBarColors = TopAppBarDefaults.topAppBarColors(
        containerColor = containerColor,
        titleContentColor = titleColor,
        navigationIconContentColor = navigationIconColor
    )
) {
    val debouncedBack = onNavigateBack?.let { rememberDebouncedClick(onClick = it) }

    TopAppBar(
        title = {
            if (subtitle != null) {
                AppTypography.TitleLarge(
                    text = title,
                    color = titleColor,
                    fontWeight = FontWeight.Bold
                )
                AppTypography.Caption(
                    text = subtitle,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            } else {
                AppTypography.TitleLarge(
                    text = title,
                    color = titleColor,
                    fontWeight = FontWeight.Bold
                )
            }
        },
        navigationIcon = {
            if (debouncedBack != null) {
                AppIconButton(
                    icon = Icons.AutoMirrored.Filled.ArrowBack,
                    onClick = debouncedBack,
                    contentDescription = stringResource(R.string.back),
                    tint = navigationIconColor
                )
            }
        },
        actions = actions,
        colors = colors,
        modifier = modifier
    )
}
