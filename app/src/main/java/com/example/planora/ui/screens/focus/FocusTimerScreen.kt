package com.example.planora.ui.screens.focus

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import com.example.planora.R
import com.example.planora.ui.components.AppTypography
import com.example.planora.ui.screens.focus.components.FocusModeSegmentedRow
import com.example.planora.ui.screens.focus.components.FocusStatsPill
import com.example.planora.ui.screens.focus.components.FocusTimerDial
import com.example.planora.ui.screens.focus.components.TimerControlButtons
import com.example.planora.ui.theme.dimensions

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FocusTimerScreen(
    uiState: FocusTimerUiState,
    onModeSelected: (FocusMode) -> Unit,
    onPlayPause: () -> Unit,
    onReset: () -> Unit
) {
    val dims = dimensions
    val isLandscape = LocalConfiguration.current.orientation == Configuration.ORIENTATION_LANDSCAPE

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    AppTypography.TitleLarge(text = stringResource(R.string.focus_title))
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        }
    ) { paddingValues ->
        if (isLandscape) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(dims.spacing.default),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.weight(1.2f)
                ) {
                    FocusTimerDial(
                        remainingSeconds = uiState.remainingSeconds,
                        totalSeconds = uiState.totalSeconds,
                        mode = uiState.mode,
                        isRunning = uiState.isRunning,
                        size = dims.components.emptyStateIconContainer * 3f
                    )
                }

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = dims.spacing.default),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    FocusModeSegmentedRow(
                        currentMode = uiState.mode,
                        onModeSelected = onModeSelected
                    )

                    Spacer(modifier = Modifier.height(dims.spacing.large))

                    TimerControlButtons(
                        isRunning = uiState.isRunning,
                        onPlayPause = onPlayPause,
                        onReset = onReset
                    )

                    Spacer(modifier = Modifier.height(dims.spacing.default))

                    FocusStatsPill(
                        minsToday = uiState.totalFocusMinutesToday,
                        sessionsToday = uiState.completedSessionsToday
                    )
                }
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(horizontal = dims.spacing.large, vertical = dims.spacing.default),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                FocusModeSegmentedRow(
                    currentMode = uiState.mode,
                    onModeSelected = onModeSelected,
                    modifier = Modifier.fillMaxWidth()
                )

                FocusTimerDial(
                    remainingSeconds = uiState.remainingSeconds,
                    totalSeconds = uiState.totalSeconds,
                    mode = uiState.mode,
                    isRunning = uiState.isRunning,
                    size = dims.components.emptyStateIconContainer * 3.8f
                )

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    TimerControlButtons(
                        isRunning = uiState.isRunning,
                        onPlayPause = onPlayPause,
                        onReset = onReset
                    )

                    Spacer(modifier = Modifier.height(dims.spacing.medium))

                    FocusStatsPill(
                        minsToday = uiState.totalFocusMinutesToday,
                        sessionsToday = uiState.completedSessionsToday
                    )

                    Spacer(modifier = Modifier.height(dims.spacing.small))
                }
            }
        }
    }
}
