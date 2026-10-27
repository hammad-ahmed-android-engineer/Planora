package com.example.planora.presentation.taskview

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun TaskViewRoute(
    onNavigateBack: () -> Unit,
    onNavigateToTaskDetail: (taskId: String) -> Unit,
    onNavigateToTaskEditor: (taskId: String) -> Unit = {},
    viewModel: TaskViewViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    TaskViewScreen(
        uiState = uiState,
        onNavigateBack = onNavigateBack,
        onNavigateToTaskDetail = onNavigateToTaskDetail,
        onNavigateToTaskEditor = onNavigateToTaskEditor,
        onToggleComplete = viewModel::toggleTaskComplete,
        onToggleFavorite = viewModel::toggleTaskFavorite,
        onTogglePin = viewModel::toggleTaskPin,
        onDeleteTask = viewModel::deleteTask,
        onEmptyTrash = viewModel::emptyTrash
    )
}
