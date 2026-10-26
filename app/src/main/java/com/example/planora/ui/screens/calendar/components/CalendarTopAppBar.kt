package com.example.planora.ui.screens.calendar.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Today
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.planora.R
import com.example.planora.ui.components.AppTypography
import com.example.planora.ui.theme.IndigoPrimary
import com.example.planora.ui.theme.LocalDimensions
import com.example.planora.util.rememberDebouncedClick

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendarTopAppBar(
    onGoToToday: () -> Unit,
    modifier: Modifier = Modifier
) {
    val dims = LocalDimensions.current
    val debouncedToday = rememberDebouncedClick(onClick = onGoToToday)

    TopAppBar(
        title = {
            AppTypography.TitleLarge(text = stringResource(R.string.calendar_title))
        },
        actions = {
            FilledTonalButton(
                onClick = debouncedToday,
                shape = RoundedCornerShape(dims.corners.small),
                colors = ButtonDefaults.filledTonalButtonColors(
                    containerColor = IndigoPrimary.copy(alpha = 0.14f),
                    contentColor = IndigoPrimary
                ),
                modifier = Modifier.padding(end = dims.spacing.small)
            ) {
                Icon(
                    imageVector = Icons.Default.Today,
                    contentDescription = null,
                    modifier = Modifier.size(dims.icons.small)
                )
                Spacer(modifier = Modifier.width(dims.spacing.tiny))
                AppTypography.SubText(
                    text = stringResource(R.string.calendar_today_button),
                    color = IndigoPrimary
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.background
        ),
        modifier = modifier
    )
}
