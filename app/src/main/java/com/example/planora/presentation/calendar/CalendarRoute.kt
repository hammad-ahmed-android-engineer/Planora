package com.example.planora.presentation.calendar

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun CalendarRoute(
    onNavigateToTaskDetail: (taskId: String) -> Unit,
    onNavigateToTaskEditor: (taskId: String) -> Unit = {},
    viewModel: CalendarViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    CalendarScreen(
        uiState = uiState,
        onNavigateToTaskDetail = onNavigateToTaskDetail,
        onNavigateToTaskEditor = onNavigateToTaskEditor,
        onDateSelected = viewModel::setSelectedDate,
        onMonthChange = viewModel::setCurrentMonth,
        onGoToToday = viewModel::goToToday,
        onToggleComplete = viewModel::toggleTaskComplete,
        onToggleFavorite = viewModel::toggleTaskFavorite,
        onTogglePin = viewModel::toggleTaskPin,
        onDeleteTask = viewModel::deleteTask
    )
}
