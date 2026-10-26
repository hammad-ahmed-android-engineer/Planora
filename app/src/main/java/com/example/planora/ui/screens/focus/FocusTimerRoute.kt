package com.example.planora.ui.screens.focus

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun FocusTimerRoute(
    viewModel: FocusTimerViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    FocusTimerScreen(
        uiState = uiState,
        onModeSelected = viewModel::setMode,
        onPlayPause = {
            if (uiState.isRunning) viewModel.pauseTimer() else viewModel.startTimer()
        },
        onReset = viewModel::resetTimer
    )
}
