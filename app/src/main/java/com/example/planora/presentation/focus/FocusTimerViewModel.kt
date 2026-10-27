package com.example.planora.presentation.focus

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.planora.domain.model.FocusSession
import com.example.planora.domain.usecase.ManageFocusUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

enum class FocusMode(val label: String, val defaultMinutes: Int) {
    FOCUS("Focus Session", 25),
    SHORT_BREAK("Short Break", 5),
    LONG_BREAK("Long Break", 15)
}

data class FocusTimerUiState(
    val mode: FocusMode = FocusMode.FOCUS,
    val remainingSeconds: Int = 25 * 60,
    val totalSeconds: Int = 25 * 60,
    val isRunning: Boolean = false,
    val completedSessionsToday: Int = 0,
    val totalFocusMinutesToday: Int = 0
)

@HiltViewModel
class FocusTimerViewModel @Inject constructor(
    private val manageFocusUseCase: ManageFocusUseCase
) : ViewModel() {

    private val _timerState = MutableStateFlow(FocusTimerUiState())
    private var timerJob: Job? = null

    val uiState: StateFlow<FocusTimerUiState> = combine(
        _timerState,
        manageFocusUseCase.getTotalFocusMinutesToday()
    ) { timer, focusMins ->
        timer.copy(totalFocusMinutesToday = focusMins)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = FocusTimerUiState()
    )

    fun setMode(mode: FocusMode) {
        timerJob?.cancel()
        val seconds = mode.defaultMinutes * 60
        _timerState.update {
            it.copy(
                mode = mode,
                remainingSeconds = seconds,
                totalSeconds = seconds,
                isRunning = false
            )
        }
    }

    fun startTimer() {
        if (_timerState.value.isRunning) return
        _timerState.update { it.copy(isRunning = true) }

        timerJob = viewModelScope.launch {
            while (_timerState.value.remainingSeconds > 0 && _timerState.value.isRunning) {
                delay(1000)
                _timerState.update { it.copy(remainingSeconds = it.remainingSeconds - 1) }
            }

            if (_timerState.value.remainingSeconds <= 0) {
                onTimerFinished()
            }
        }
    }

    fun pauseTimer() {
        timerJob?.cancel()
        _timerState.update { it.copy(isRunning = false) }
    }

    fun resetTimer() {
        timerJob?.cancel()
        val seconds = _timerState.value.mode.defaultMinutes * 60
        _timerState.update {
            it.copy(
                remainingSeconds = seconds,
                totalSeconds = seconds,
                isRunning = false
            )
        }
    }

    private fun onTimerFinished() {
        val currentState = _timerState.value
        _timerState.update { it.copy(isRunning = false) }

        viewModelScope.launch {
            manageFocusUseCase.insertFocusSession(
                FocusSession(
                    durationMinutes = currentState.mode.defaultMinutes,
                    sessionType = currentState.mode.name,
                    isCompleted = true
                )
            )

            if (currentState.mode == FocusMode.FOCUS) {
                _timerState.update { it.copy(completedSessionsToday = it.completedSessionsToday + 1) }
            }

            // Reset for next round
            resetTimer()
        }
    }
}
