package com.example.planora.presentation.stats

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.planora.domain.model.ProductivityStats
import com.example.planora.domain.model.TaskViewType
import com.example.planora.domain.usecase.CalculateProductivityStatsUseCase
import com.example.planora.domain.usecase.GetTasksUseCase
import com.example.planora.domain.usecase.ManageFocusUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

data class StatsUiState(
    val stats: ProductivityStats = ProductivityStats(),
    val isLoading: Boolean = false
)

@HiltViewModel
class StatsViewModel @Inject constructor(
    getTasksUseCase: GetTasksUseCase,
    manageFocusUseCase: ManageFocusUseCase,
    calculateProductivityStatsUseCase: CalculateProductivityStatsUseCase
) : ViewModel() {

    val uiState: StateFlow<StatsUiState> = combine(
        getTasksUseCase.getAllActiveTasks(),
        getTasksUseCase.getSmartListTasks(TaskViewType.COMPLETED),
        manageFocusUseCase.getTotalFocusMinutesToday(),
        manageFocusUseCase.getTotalFocusMinutesThisWeek()
    ) { active, completed, focusToday, focusWeek ->
        calculateProductivityStatsUseCase.execute(active, completed, focusToday, focusWeek)
    }.map { statsResult ->
        StatsUiState(stats = statsResult, isLoading = false)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = StatsUiState(isLoading = true)
    )
}
