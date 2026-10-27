package com.example.planora.presentation.search

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun SearchFilterRoute(
    onNavigateToTaskDetail: (taskId: String) -> Unit,
    onNavigateToTaskEditor: (taskId: String) -> Unit = {},
    viewModel: SearchFilterViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    SearchFilterScreen(
        uiState = uiState,
        onNavigateToTaskDetail = onNavigateToTaskDetail,
        onNavigateToTaskEditor = onNavigateToTaskEditor,
        onQueryChange = viewModel::updateQuery,
        onSelectPriority = viewModel::setPriority,
        onClearFilters = viewModel::clearFilters,
        onToggleComplete = viewModel::toggleTaskComplete,
        onToggleFavorite = viewModel::toggleTaskFavorite,
        onTogglePin = viewModel::toggleTaskPin,
        onDeleteTask = viewModel::deleteTask
    )
}
