package com.example.planora.presentation.taskdetail

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
import androidx.compose.ui.res.stringResource
import com.example.planora.R
import com.example.planora.domain.model.Subtask
import com.example.planora.core.designsystem.components.EmptyStateView
import com.example.planora.presentation.taskdetail.components.TaskDetailQuickActions
import com.example.planora.presentation.taskdetail.components.TaskDetailTopAppBar
import com.example.planora.presentation.taskdetail.components.TaskHeaderCard
import com.example.planora.presentation.taskdetail.components.TaskNotesCard
import com.example.planora.presentation.taskdetail.components.TaskScheduleCard
import com.example.planora.presentation.taskdetail.components.TaskSubtasksCard
import com.example.planora.core.designsystem.theme.dimensions
import com.example.planora.core.utils.rememberDebouncedClick

@Composable
fun TaskDetailScreen(
    uiState: TaskDetailUiState,
    onNavigateBack: () -> Unit,
    onNavigateToEdit: (taskId: String) -> Unit,
    onToggleComplete: () -> Unit,
    onToggleFavorite: () -> Unit,
    onTogglePin: () -> Unit,
    onToggleSubtask: (Subtask) -> Unit,
    onPostpone: () -> Unit,
    onDeleteTask: () -> Unit
) {
    val dims = dimensions
    val task = uiState.task
    val isLandscape = LocalConfiguration.current.orientation == Configuration.ORIENTATION_LANDSCAPE
    val debouncedBack = rememberDebouncedClick(onClick = onNavigateBack)

    Scaffold(
        topBar = {
            TaskDetailTopAppBar(
                task = task,
                onNavigateBack = onNavigateBack,
                onNavigateToEdit = onNavigateToEdit,
                onTogglePin = onTogglePin,
                onToggleFavorite = onToggleFavorite,
                onDeleteTask = onDeleteTask
            )
        }
    ) { paddingValues ->
        if (task == null && !uiState.isLoading) {
            EmptyStateView(
                title = stringResource(R.string.task_detail_not_found_title),
                subtitle = stringResource(R.string.task_detail_not_found_subtitle),
                actionText = stringResource(R.string.task_detail_go_back),
                onActionClick = debouncedBack
            )
        } else if (task != null) {
            if (isLandscape) {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .padding(dims.spacing.default)
                ) {
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .verticalScroll(rememberScrollState())
                            .padding(end = dims.spacing.medium)
                    ) {
                        TaskHeaderCard(
                            task = task,
                            onToggleComplete = onToggleComplete
                        )

                        Spacer(modifier = Modifier.height(dims.spacing.medium))

                        TaskScheduleCard(task = task)
                    }

                    Column(
                        modifier = Modifier
                            .weight(1.2f)
                            .verticalScroll(rememberScrollState())
                            .padding(start = dims.spacing.medium)
                    ) {
                        if (task.subtasks.isNotEmpty()) {
                            TaskSubtasksCard(
                                task = task,
                                onToggleSubtask = onToggleSubtask
                            )
                            Spacer(modifier = Modifier.height(dims.spacing.medium))
                        }

                        if (task.notes.isNotBlank()) {
                            TaskNotesCard(notes = task.notes)
                            Spacer(modifier = Modifier.height(dims.spacing.medium))
                        }

                        TaskDetailQuickActions(
                            onPostpone = onPostpone,
                            onEdit = { onNavigateToEdit(task.id) }
                        )
                    }
                }
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .verticalScroll(rememberScrollState())
                        .padding(horizontal = dims.spacing.default, vertical = dims.spacing.small)
                ) {
                    TaskHeaderCard(
                        task = task,
                        onToggleComplete = onToggleComplete
                    )

                    Spacer(modifier = Modifier.height(dims.spacing.medium))

                    TaskScheduleCard(task = task)

                    Spacer(modifier = Modifier.height(dims.spacing.medium))

                    if (task.subtasks.isNotEmpty()) {
                        TaskSubtasksCard(
                            task = task,
                            onToggleSubtask = onToggleSubtask
                        )
                        Spacer(modifier = Modifier.height(dims.spacing.medium))
                    }

                    if (task.notes.isNotBlank()) {
                        TaskNotesCard(notes = task.notes)
                        Spacer(modifier = Modifier.height(dims.spacing.medium))
                    }

                    TaskDetailQuickActions(
                        onPostpone = onPostpone,
                        onEdit = { onNavigateToEdit(task.id) }
                    )

                    Spacer(modifier = Modifier.height(dims.spacing.large))
                }
            }
        }
    }
}
