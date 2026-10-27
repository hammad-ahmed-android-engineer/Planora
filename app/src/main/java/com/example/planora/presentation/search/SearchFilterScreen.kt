package com.example.planora.presentation.search

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import com.example.planora.domain.model.Priority
import com.example.planora.domain.model.Task
import com.example.planora.presentation.search.components.PriorityFilterSection
import com.example.planora.presentation.search.components.SearchInputField
import com.example.planora.presentation.search.components.SearchResultsHeader
import com.example.planora.presentation.search.components.SearchResultsList
import com.example.planora.presentation.search.components.SearchTopBar
import com.example.planora.core.designsystem.theme.dimensions
import com.example.planora.core.utils.rememberDebouncedClick

@Composable
fun SearchFilterScreen(
    uiState: SearchFilterUiState,
    onNavigateToTaskDetail: (taskId: String) -> Unit,
    onNavigateToTaskEditor: (taskId: String) -> Unit = {},
    onQueryChange: (String) -> Unit,
    onSelectPriority: (Priority?) -> Unit,
    onClearFilters: () -> Unit,
    onToggleComplete: (Task) -> Unit,
    onToggleFavorite: (Task) -> Unit,
    onTogglePin: (Task) -> Unit,
    onDeleteTask: (Task) -> Unit
) {
    val dims = dimensions
    val isLandscape = LocalConfiguration.current.orientation == Configuration.ORIENTATION_LANDSCAPE
    val debouncedClear = rememberDebouncedClick(onClick = onClearFilters)

    val hasActiveFilters = uiState.query.isNotBlank() || uiState.selectedPriority != null || uiState.selectedCategoryId != null

    Scaffold(
        topBar = {
            SearchTopBar(
                hasActiveFilters = hasActiveFilters,
                onClearFilters = debouncedClear
            )
        }
    ) { paddingValues ->
        if (isLandscape) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(dims.spacing.default)
            ) {
                Column(
                    modifier = Modifier
                        .weight(1.1f)
                        .verticalScroll(rememberScrollState())
                        .padding(end = dims.spacing.medium)
                ) {
                    SearchInputField(
                        query = uiState.query,
                        onQueryChange = onQueryChange
                    )

                    Spacer(modifier = Modifier.height(dims.spacing.medium))

                    PriorityFilterSection(
                        selectedPriority = uiState.selectedPriority,
                        onSelectPriority = onSelectPriority
                    )
                }

                Column(
                    modifier = Modifier
                        .weight(1.4f)
                        .fillMaxSize()
                ) {
                    SearchResultsHeader(count = uiState.searchResults.size)

                    Spacer(modifier = Modifier.height(dims.spacing.small))

                    SearchResultsList(
                        tasks = uiState.searchResults,
                        onTaskClick = onNavigateToTaskEditor,
                        onEditTask = onNavigateToTaskEditor,
                        onToggleComplete = onToggleComplete,
                        onToggleFavorite = onToggleFavorite,
                        onTogglePin = onTogglePin,
                        onDeleteTask = onDeleteTask,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(horizontal = dims.spacing.default)
            ) {
                SearchInputField(
                    query = uiState.query,
                    onQueryChange = onQueryChange
                )

                Spacer(modifier = Modifier.height(dims.spacing.medium))

                PriorityFilterSection(
                    selectedPriority = uiState.selectedPriority,
                    onSelectPriority = onSelectPriority
                )

                Spacer(modifier = Modifier.height(dims.spacing.medium))

                SearchResultsHeader(count = uiState.searchResults.size)

                Spacer(modifier = Modifier.height(dims.spacing.small))

                SearchResultsList(
                    tasks = uiState.searchResults,
                    onTaskClick = onNavigateToTaskEditor,
                    onEditTask = onNavigateToTaskEditor,
                    onToggleComplete = onToggleComplete,
                    onToggleFavorite = onToggleFavorite,
                    onTogglePin = onTogglePin,
                    onDeleteTask = onDeleteTask,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}
