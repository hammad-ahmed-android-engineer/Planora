package com.example.planora.ui.screens.search.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.planora.R
import com.example.planora.domain.model.Task
import com.example.planora.ui.components.EmptyStateView
import com.example.planora.ui.components.TaskItemCard

@Composable
fun SearchResultsList(
    tasks: List<Task>,
    onTaskClick: (String) -> Unit,
    onEditTask: (String) -> Unit,
    onToggleComplete: (Task) -> Unit,
    onToggleFavorite: (Task) -> Unit,
    onTogglePin: (Task) -> Unit,
    onDeleteTask: (Task) -> Unit,
    modifier: Modifier = Modifier
) {
    if (tasks.isEmpty()) {
        EmptyStateView(
            title = stringResource(R.string.search_no_matching_title),
            subtitle = stringResource(R.string.search_no_matching_subtitle),
            icon = Icons.Default.Search,
            modifier = modifier.fillMaxSize()
        )
    } else {
        LazyColumn(modifier = modifier.fillMaxSize()) {
            items(
                items = tasks,
                key = { it.id }
            ) { task ->
                TaskItemCard(
                    task = task,
                    onTaskClick = { onTaskClick(task.id) },
                    onEditTask = { onEditTask(task.id) },
                    onToggleComplete = { onToggleComplete(task) },
                    onToggleFavorite = { onToggleFavorite(task) },
                    onTogglePin = { onTogglePin(task) },
                    onDeleteTask = { onDeleteTask(task) },
                    onPostponeTask = {},
                    modifier = Modifier.animateItem()
                )
            }
        }
    }
}
