package com.example.planora.ui.screens.calendar.components

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.planora.R
import com.example.planora.domain.model.Task
import com.example.planora.ui.components.EmptyStateView
import com.example.planora.ui.components.TaskItemCard

@Composable
fun CalendarAgendaTaskList(
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
            title = stringResource(R.string.calendar_no_tasks_title),
            subtitle = stringResource(R.string.calendar_no_tasks_subtitle),
            icon = Icons.Default.CalendarMonth,
            modifier = modifier
        )
    } else {
        LazyColumn(modifier = modifier) {
            items(
                count = tasks.size,
                key = { tasks[it].id }
            ) { index ->
                val task = tasks[index]
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
