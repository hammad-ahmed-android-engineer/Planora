package com.example.planora.core.designsystem.theme

import android.os.Build
import androidx.activity.ComponentActivity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import com.example.planora.core.utils.toColor

@Composable
fun PlanoraTheme(
    themeMode: String = "SYSTEM", // LIGHT, DARK, SYSTEM
    accentColorHex: String = "#6366F1",
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val darkTheme = when (themeMode.uppercase()) {
        "LIGHT" -> false
        "DARK" -> true
        else -> isSystemInDarkTheme()
    }

    val primaryColor = parseColorHex(accentColorHex, IndigoPrimary)

    val lightScheme = lightColorScheme(
        primary = primaryColor,
        onPrimary = Color.White,
        primaryContainer = primaryColor.copy(alpha = 0.12f),
        onPrimaryContainer = primaryColor,
        secondary = EmeraldPrimary,
        onSecondary = Color.White,
        secondaryContainer = EmeraldContainerLight,
        onSecondaryContainer = Color(0xFF065F46),
        tertiary = AmberPrimary,
        onTertiary = Color.White,
        tertiaryContainer = AmberContainerLight,
        onTertiaryContainer = Color(0xFF92400E),
        background = BackgroundLight,
        onBackground = TextPrimaryLight,
        surface = SurfaceLight,
        onSurface = TextPrimaryLight,
        surfaceVariant = CardElevatedLight,
        onSurfaceVariant = TextSecondaryLight,
        outline = BorderLight,
        outlineVariant = BorderSubtleLight,
        error = PriorityUrgent,
        onError = Color.White
    )

    val darkScheme = darkColorScheme(
        primary = primaryColor,
        onPrimary = Color.White,
        primaryContainer = primaryColor.copy(alpha = 0.22f),
        onPrimaryContainer = Color(0xFFC7D2FE),
        secondary = EmeraldPrimary,
        onSecondary = Color.Black,
        secondaryContainer = EmeraldContainerDark,
        onSecondaryContainer = Color(0xFFA7F3D0),
        tertiary = AmberPrimary,
        onTertiary = Color.Black,
        tertiaryContainer = AmberContainerDark,
        onTertiaryContainer = Color(0xFFFDE68A),
        background = BackgroundDark,
        onBackground = TextPrimaryDark,
        surface = SurfaceDark,
        onSurface = TextPrimaryDark,
        surfaceVariant = CardDark,
        onSurfaceVariant = TextSecondaryDark,
        outline = BorderDark,
        outlineVariant = BorderSubtleDark,
        error = PriorityUrgent,
        onError = Color.White
    )

    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> darkScheme
        else -> lightScheme
    }

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as? ComponentActivity)?.window
                ?: (view.context as? android.app.Activity)?.window
            window?.let { w ->
                val controller = WindowCompat.getInsetsController(w, view)
                controller.isAppearanceLightStatusBars = !darkTheme
                controller.isAppearanceLightNavigationBars = !darkTheme
            }
        }
    }

    val dimensions = resolveDimensions()
    val typography = remember(dimensions) { AppTypography.material3Typography(dimensions.typeScale) }
    val shapes = remember(dimensions) {
        Shapes(
            extraSmall = RoundedCornerShape(dimensions.corners.micro),
            small = RoundedCornerShape(dimensions.corners.small),
            medium = RoundedCornerShape(dimensions.corners.medium),
            large = RoundedCornerShape(dimensions.corners.large),
            extraLarge = RoundedCornerShape(dimensions.corners.extraLarge)
        )
    }

    CompositionLocalProvider(
        LocalDimensions provides dimensions
    ) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = typography,
            shapes = shapes,
            content = content
        )
    }
}

fun parseColorHex(hexStr: String, fallback: Color = IndigoPrimary): Color =
    hexStr.toColor(fallback)
