package com.example.planora.presentation.home.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.example.planora.R
import com.example.planora.core.designsystem.theme.AppTypography
import com.example.planora.presentation.home.HomeFilterTab
import com.example.planora.core.designsystem.theme.dimensions
import com.example.planora.core.utils.clickableDebounced

@Composable
fun FilterTabsRow(
    selectedTab: HomeFilterTab,
    onTabSelected: (HomeFilterTab) -> Unit,
    todayCount: Int,
    pinnedCount: Int,
    overdueCount: Int,
    allCount: Int,
    modifier: Modifier = Modifier
) {
    val dims = dimensions
    val primaryColor = MaterialTheme.colorScheme.primary
    LazyRow(
        modifier = modifier.fillMaxWidth(),
        contentPadding = PaddingValues(horizontal = dims.spacing.default),
        horizontalArrangement = Arrangement.spacedBy(dims.spacing.small)
    ) {
        items(HomeFilterTab.entries.toTypedArray()) { tab ->
            val isSelected = selectedTab == tab
            val tabTitle = when (tab) {
                HomeFilterTab.TODAY -> stringResource(R.string.tab_today)
                HomeFilterTab.PINNED -> stringResource(R.string.tab_pinned)
                HomeFilterTab.OVERDUE -> stringResource(R.string.tab_overdue)
                HomeFilterTab.ALL -> stringResource(R.string.tab_all)
            }
            val count = when (tab) {
                HomeFilterTab.TODAY -> todayCount
                HomeFilterTab.PINNED -> pinnedCount
                HomeFilterTab.OVERDUE -> overdueCount
                HomeFilterTab.ALL -> allCount
            }

            val bgColor by animateColorAsState(
                targetValue = if (isSelected) primaryColor else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                animationSpec = tween(200),
                label = "tabBg"
            )
            val textColor by animateColorAsState(
                targetValue = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurfaceVariant,
                animationSpec = tween(200),
                label = "tabText"
            )

            val shape = RoundedCornerShape(dims.corners.medium)

            Box(
                modifier = Modifier
                    .clip(shape)
                    .background(bgColor)
                    .clickableDebounced { onTabSelected(tab) }
                    .padding(horizontal = dims.spacing.medium, vertical = dims.spacing.small),
                contentAlignment = Alignment.Center
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    AppTypography.SubText(
                        text = tabTitle,
                        color = textColor
                    )
                    if (count > 0) {
                        Spacer(modifier = Modifier.width(dims.spacing.tiny))
                        Box(
                            modifier = Modifier
                                .clip(CircleShape)
                                .background(if (isSelected) Color.White.copy(alpha = 0.25f) else MaterialTheme.colorScheme.surface)
                                .padding(horizontal = dims.spacing.small, vertical = dims.spacing.micro)
                        ) {
                            AppTypography.Caption(
                                text = count.toString(),
                                fontWeight = FontWeight.Bold,
                                color = if (isSelected) primaryColor else MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                }
            }
        }
    }
}
