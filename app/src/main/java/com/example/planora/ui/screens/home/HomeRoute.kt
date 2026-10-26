package com.example.planora.ui.screens.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun HomeRoute(
    onNavigateToTaskEditor: (taskId: String?) -> Unit,
    onNavigateToTaskDetail: (taskId: String) -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    HomeScreen(
        uiState = uiState,
        onNavigateToTaskEditor = onNavigateToTaskEditor,
        onNavigateToTaskDetail = onNavigateToTaskDetail,
        onToggleComplete = viewModel::toggleTaskComplete,
        onToggleFavorite = viewModel::toggleTaskFavorite,
        onTogglePin = viewModel::toggleTaskPin,
        onDeleteTask = viewModel::deleteTask,
        onPostponeTask = viewModel::postponeTaskToTomorrow,
        onSelectNotificationStyle = viewModel::selectNotificationStyle
    )
}
