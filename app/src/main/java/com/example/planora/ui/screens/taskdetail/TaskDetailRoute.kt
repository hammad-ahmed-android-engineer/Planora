package com.example.planora.ui.screens.taskdetail

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun TaskDetailRoute(
    onNavigateBack: () -> Unit,
    onNavigateToEdit: (taskId: String) -> Unit,
    viewModel: TaskDetailViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    TaskDetailScreen(
        uiState = uiState,
        onNavigateBack = onNavigateBack,
        onNavigateToEdit = onNavigateToEdit,
        onToggleComplete = viewModel::toggleComplete,
        onToggleFavorite = viewModel::toggleFavorite,
        onTogglePin = viewModel::togglePin,
        onToggleSubtask = viewModel::toggleSubtask,
        onPostpone = viewModel::postponeToTomorrow,
        onDeleteTask = {
            viewModel.deleteTask()
            onNavigateBack()
        }
    )
}
