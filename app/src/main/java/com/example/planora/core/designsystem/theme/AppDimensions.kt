package com.example.planora.core.designsystem.theme

import androidx.annotation.DimenRes
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import com.intuit.sdp.R.dimen as sdp
import com.intuit.ssp.R.dimen as ssp

/*
 * ─────────────────────────────────────────────────────────────────────────────
 * Planora design tokens (sizes)
 * ─────────────────────────────────────────────────────────────────────────────
 *
 * Single source of truth for every size used in the UI: spacing, corner radii,
 * icon sizes, component sizes, borders, elevation and text sizes.
 */

// region Token groups

/** Gaps between elements: padding, margins and arrangement spacing. */
@Immutable
data class Spacing(
    val zero: Dp,
    val micro: Dp,
    val tiny: Dp,
    val small: Dp,
    val medium: Dp,
    val default: Dp,
    val large: Dp,
    val extraLarge: Dp,
)

/** Corner radii for shapes (cards, buttons, sheets, chips). */
@Immutable
data class Corners(
    val micro: Dp,
    val small: Dp,
    val medium: Dp,
    val large: Dp,
    val extraLarge: Dp,
    /** Fully rounded ends, e.g. pill-shaped buttons and search bars. */
    val pill: Dp,
)

/** Icon sizes, from small inline glyphs up to large illustrative icons. */
@Immutable
data class IconSizes(
    val micro: Dp,
    val tiny: Dp,
    val small: Dp,
    val medium: Dp,
    val large: Dp,
    val extraLarge: Dp,
    val huge: Dp,
)

/** Fixed sizes of specific reusable components. */
@Immutable
data class ComponentSizes(
    val buttonHeight: Dp,
    val buttonSmallHeight: Dp,
    val searchBarHeight: Dp,
    val dialogPadding: Dp,
    val fab: Dp,
    val avatar: Dp,
    val emptyStateIconContainer: Dp,
    val colorPickerCircle: Dp,
)

/** Stroke widths for outlines and dividers. */
@Immutable
data class Borders(
    val thin: Dp,
    val medium: Dp,
    val thick: Dp,
)

/** Shadow elevation levels. */
@Immutable
data class Elevations(
    val none: Dp,
    val low: Dp,
    val medium: Dp,
    val high: Dp,
)

/**
 * General-purpose text sizes for ad-hoc use.
 * For standard text, prefer the Material roles in [TypeScale] (via AppTypography).
 */
@Immutable
data class TextSizes(
    val caption: TextUnit,
    val small: TextUnit,
    val medium: TextUnit,
    val large: TextUnit,
    val title: TextUnit,
    val headline: TextUnit,
    val display: TextUnit,
)

/** Font size and line height for a single typography role. Both scale with SSP. */
@Immutable
data class TypeMetric(
    val fontSize: TextUnit,
    val lineHeight: TextUnit,
)

/**
 * Sizes for every Material 3 type role (display, headline, title, body, label).
 * AppTypography combines these with weights and letter spacing to build the
 * theme's `Typography`, so text sizes are defined only here.
 */
@Immutable
data class TypeScale(
    val displayLarge: TypeMetric,
    val displayMedium: TypeMetric,
    val displaySmall: TypeMetric,
    val headlineLarge: TypeMetric,
    val headlineMedium: TypeMetric,
    val headlineSmall: TypeMetric,
    val titleLarge: TypeMetric,
    val titleMedium: TypeMetric,
    val titleSmall: TypeMetric,
    val bodyLarge: TypeMetric,
    val bodyMedium: TypeMetric,
    val bodySmall: TypeMetric,
    val labelLarge: TypeMetric,
    val labelMedium: TypeMetric,
    val labelSmall: TypeMetric,
)

/**
 * Root container holding every token group.
 * It is immutable, so Compose can skip recomposition when it hasn't changed.
 */
@Immutable
data class Dimensions(
    val spacing: Spacing,
    val corners: Corners,
    val icons: IconSizes,
    val components: ComponentSizes,
    val borders: Borders,
    val elevations: Elevations,
    val textSizes: TextSizes,
    val typeScale: TypeScale,
)

@Composable
@ReadOnlyComposable
private fun sspResource(@DimenRes id: Int): TextUnit =
    with(LocalDensity.current) { dimensionResource(id).toSp() }

@Composable
@ReadOnlyComposable
private fun typeMetric(@DimenRes fontSize: Int, @DimenRes lineHeight: Int) =
    TypeMetric(sspResource(fontSize), sspResource(lineHeight))

@Composable
@ReadOnlyComposable
fun resolveDimensions(): Dimensions = Dimensions(
    spacing = Spacing(
        zero = 0.dp,
        micro = dimensionResource(sdp._2sdp),
        tiny = dimensionResource(sdp._3sdp),
        small = dimensionResource(sdp._6sdp),
        medium = dimensionResource(sdp._10sdp),
        default = dimensionResource(sdp._14sdp),
        large = dimensionResource(sdp._20sdp),
        extraLarge = dimensionResource(sdp._28sdp),
    ),
    corners = Corners(
        micro = dimensionResource(sdp._3sdp),
        small = dimensionResource(sdp._6sdp),
        medium = dimensionResource(sdp._10sdp),
        large = dimensionResource(sdp._14sdp),
        extraLarge = dimensionResource(sdp._18sdp),
        pill = dimensionResource(sdp._24sdp),
    ),
    icons = IconSizes(
        micro = dimensionResource(sdp._8sdp),
        tiny = dimensionResource(sdp._10sdp),
        small = dimensionResource(sdp._14sdp),
        medium = dimensionResource(sdp._18sdp),
        large = dimensionResource(sdp._22sdp),
        extraLarge = dimensionResource(sdp._30sdp),
        huge = dimensionResource(sdp._40sdp),
    ),
    components = ComponentSizes(
        buttonHeight = dimensionResource(sdp._42sdp),
        buttonSmallHeight = dimensionResource(sdp._32sdp),
        searchBarHeight = dimensionResource(sdp._42sdp),
        dialogPadding = dimensionResource(sdp._20sdp),
        fab = dimensionResource(sdp._52sdp),
        avatar = dimensionResource(sdp._36sdp),
        emptyStateIconContainer = dimensionResource(sdp._60sdp),
        colorPickerCircle = dimensionResource(sdp._34sdp),
    ),
    borders = Borders(
        thin = dimensionResource(sdp._1sdp),
        medium = dimensionResource(sdp._2sdp),
        thick = dimensionResource(sdp._3sdp),
    ),
    elevations = Elevations(
        none = 0.dp,
        low = dimensionResource(sdp._2sdp),
        medium = dimensionResource(sdp._4sdp),
        high = dimensionResource(sdp._8sdp),
    ),
    textSizes = TextSizes(
        caption = sspResource(ssp._9ssp),
        small = sspResource(ssp._11ssp),
        medium = sspResource(ssp._12ssp),
        large = sspResource(ssp._14ssp),
        title = sspResource(ssp._16ssp),
        headline = sspResource(ssp._19ssp),
        display = sspResource(ssp._24ssp),
    ),
    typeScale = TypeScale(
        displayLarge = typeMetric(ssp._28ssp, ssp._34ssp),
        displayMedium = typeMetric(ssp._24ssp, ssp._30ssp),
        displaySmall = typeMetric(ssp._20ssp, ssp._26ssp),
        headlineLarge = typeMetric(ssp._19ssp, ssp._24ssp),
        headlineMedium = typeMetric(ssp._17ssp, ssp._22ssp),
        headlineSmall = typeMetric(ssp._15ssp, ssp._20ssp),
        titleLarge = typeMetric(ssp._16ssp, ssp._22ssp),
        titleMedium = typeMetric(ssp._14ssp, ssp._18ssp),
        titleSmall = typeMetric(ssp._12ssp, ssp._16ssp),
        bodyLarge = typeMetric(ssp._14ssp, ssp._19ssp),
        bodyMedium = typeMetric(ssp._12ssp, ssp._16ssp),
        bodySmall = typeMetric(ssp._11ssp, ssp._14ssp),
        labelLarge = typeMetric(ssp._12ssp, ssp._16ssp),
        labelMedium = typeMetric(ssp._11ssp, ssp._14ssp),
        labelSmall = typeMetric(ssp._9ssp, ssp._12ssp),
    ),
)

val LocalDimensions = staticCompositionLocalOf<Dimensions> {
    error("Dimensions not provided. Wrap your content in PlanoraTheme.")
}

val MaterialTheme.dimensions: Dimensions
    @Composable
    @ReadOnlyComposable
    get() = LocalDimensions.current

val dimensions: Dimensions
    @Composable
    @ReadOnlyComposable
    get() = LocalDimensions.current
