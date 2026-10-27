package com.example.planora.presentation.taskview

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import com.example.planora.R
import com.example.planora.domain.model.Task
import com.example.planora.core.designsystem.components.ConfirmationDialog
import com.example.planora.presentation.taskview.components.TaskViewContent
import com.example.planora.presentation.taskview.components.TaskViewTopAppBar
import com.example.planora.core.utils.rememberDebouncedClick

@Composable
fun TaskViewScreen(
    uiState: TaskViewUiState,
    onNavigateBack: () -> Unit,
    onNavigateToTaskDetail: (taskId: String) -> Unit,
    onNavigateToTaskEditor: (taskId: String) -> Unit = {},
    onToggleComplete: (Task) -> Unit,
    onToggleFavorite: (Task) -> Unit,
    onTogglePin: (Task) -> Unit,
    onDeleteTask: (Task) -> Unit,
    onEmptyTrash: () -> Unit
) {
    val tasks = uiState.tasks
    var showEmptyTrashDialog by rememberSaveable { mutableStateOf(false) }
    val isLandscape = LocalConfiguration.current.orientation == Configuration.ORIENTATION_LANDSCAPE

    val debouncedBack = rememberDebouncedClick(onClick = onNavigateBack)
    val debouncedOpenTrashConfirm = rememberDebouncedClick { showEmptyTrashDialog = true }

    Scaffold(
        topBar = {
            TaskViewTopAppBar(
                viewType = uiState.viewType,
                hasTasks = tasks.isNotEmpty(),
                onNavigateBack = debouncedBack,
                onOpenTrashConfirm = debouncedOpenTrashConfirm
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            TaskViewContent(
                tasks = tasks,
                viewType = uiState.viewType,
                isLandscape = isLandscape,
                onTaskClick = onNavigateToTaskEditor,
                onEditTask = onNavigateToTaskEditor,
                onToggleComplete = onToggleComplete,
                onToggleFavorite = onToggleFavorite,
                onTogglePin = onTogglePin,
                onDeleteTask = onDeleteTask
            )
        }

        if (showEmptyTrashDialog) {
            ConfirmationDialog(
                title = stringResource(R.string.trash_empty_confirm_title),
                message = stringResource(R.string.trash_empty_confirm_msg),
                confirmText = stringResource(R.string.trash_empty_button),
                isDestructive = true,
                onConfirm = {
                    onEmptyTrash()
                    showEmptyTrashDialog = false
                },
                onDismiss = { showEmptyTrashDialog = false }
            )
        }
    }
}
