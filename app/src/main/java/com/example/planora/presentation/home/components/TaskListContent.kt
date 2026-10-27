package com.example.planora.presentation.home.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.planora.R
import com.example.planora.domain.model.Task
import com.example.planora.core.designsystem.components.EmptyStateView
import com.example.planora.core.designsystem.components.TaskItemCard
import com.example.planora.presentation.home.HomeFilterTab
import com.example.planora.presentation.home.HomeUiState
import com.example.planora.core.designsystem.theme.dimensions

@Composable
fun TaskListContent(
    selectedTab: HomeFilterTab,
    uiState: HomeUiState,
    onNavigateToTaskEditor: (taskId: String?) -> Unit,
    onNavigateToTaskDetail: (taskId: String) -> Unit,
    onToggleComplete: (Task) -> Unit,
    onToggleFavorite: (Task) -> Unit,
    onTogglePin: (Task) -> Unit,
    onDeleteTask: (Task) -> Unit,
    onPostponeTask: (Task) -> Unit,
    modifier: Modifier = Modifier
) {
    val dims = dimensions
    val tasksToShow = when (selectedTab) {
        HomeFilterTab.TODAY -> uiState.todayTasks
        HomeFilterTab.PINNED -> uiState.pinnedTasks
        HomeFilterTab.OVERDUE -> uiState.overdueTasks
        HomeFilterTab.ALL -> (uiState.todayTasks + uiState.pinnedTasks + uiState.overdueTasks).distinctBy { it.id }
    }

    if (tasksToShow.isEmpty()) {
        EmptyStateView(
            title = when (selectedTab) {
                HomeFilterTab.TODAY -> stringResource(R.string.empty_today_title)
                HomeFilterTab.PINNED -> stringResource(R.string.empty_pinned_title)
                HomeFilterTab.OVERDUE -> stringResource(R.string.empty_overdue_title)
                HomeFilterTab.ALL -> stringResource(R.string.empty_all_title)
            },
            subtitle = when (selectedTab) {
                HomeFilterTab.TODAY -> stringResource(R.string.empty_today_subtitle)
                HomeFilterTab.PINNED -> stringResource(R.string.empty_pinned_subtitle)
                HomeFilterTab.OVERDUE -> stringResource(R.string.empty_overdue_subtitle)
                HomeFilterTab.ALL -> stringResource(R.string.empty_all_subtitle)
            },
            actionText = stringResource(R.string.empty_create_task),
            onActionClick = { onNavigateToTaskEditor(null) },
            modifier = modifier
        )
    } else {
        LazyColumn(
            modifier = modifier.fillMaxWidth()
        ) {
            items(
                items = tasksToShow,
                key = { it.id }
            ) { task ->
                TaskItemCard(
                    task = task,
                    onTaskClick = { onNavigateToTaskEditor(task.id) },
                    onEditTask = { onNavigateToTaskEditor(task.id) },
                    onToggleComplete = onToggleComplete,
                    onToggleFavorite = onToggleFavorite,
                    onTogglePin = onTogglePin,
                    onDeleteTask = onDeleteTask,
                    onPostponeTask = onPostponeTask,
                    modifier = Modifier.animateItem()
                )
            }
            item {
                Spacer(modifier = Modifier.height(dims.components.emptyStateIconContainer))
            }
        }
    }
}
