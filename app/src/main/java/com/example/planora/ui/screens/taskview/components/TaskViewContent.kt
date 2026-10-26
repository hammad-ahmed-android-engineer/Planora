package com.example.planora.ui.screens.taskview.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.planora.R
import com.example.planora.domain.model.Task
import com.example.planora.domain.model.TaskViewType
import com.example.planora.ui.components.EmptyStateView
import com.example.planora.ui.components.TaskItemCard
import com.example.planora.ui.theme.dimensions

@Composable
fun TaskViewContent(
    tasks: List<Task>,
    viewType: TaskViewType,
    isLandscape: Boolean,
    onTaskClick: (String) -> Unit,
    onEditTask: (String) -> Unit,
    onToggleComplete: (Task) -> Unit,
    onToggleFavorite: (Task) -> Unit,
    onTogglePin: (Task) -> Unit,
    onDeleteTask: (Task) -> Unit,
    modifier: Modifier = Modifier
) {
    val dims = dimensions

    if (tasks.isEmpty()) {
        EmptyStateView(
            title = stringResource(R.string.task_view_no_tasks_title, viewType.title),
            subtitle = stringResource(R.string.task_view_no_tasks_subtitle),
            modifier = modifier.fillMaxSize()
        )
    } else if (isLandscape) {
        LazyVerticalGrid(
            columns = GridCells.Adaptive(dims.components.emptyStateIconContainer * 4),
            modifier = modifier
                .fillMaxSize()
                .padding(dims.spacing.small)
        ) {
            items(tasks, key = { it.id }) { task ->
                TaskItemCard(
                    task = task,
                    onTaskClick = { onTaskClick(task.id) },
                    onEditTask = { onEditTask(task.id) },
                    onToggleComplete = onToggleComplete,
                    onToggleFavorite = onToggleFavorite,
                    onTogglePin = onTogglePin,
                    onDeleteTask = onDeleteTask,
                    onPostponeTask = {},
                    modifier = Modifier.animateItem()
                )
            }
        }
    } else {
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .padding(horizontal = dims.spacing.medium, vertical = dims.spacing.small)
        ) {
            items(tasks, key = { it.id }) { task ->
                TaskItemCard(
                    task = task,
                    onTaskClick = { onTaskClick(task.id) },
                    onEditTask = { onEditTask(task.id) },
                    onToggleComplete = onToggleComplete,
                    onToggleFavorite = onToggleFavorite,
                    onTogglePin = onTogglePin,
                    onDeleteTask = onDeleteTask,
                    onPostponeTask = {},
                    modifier = Modifier.animateItem()
                )
            }
        }
    }
}
