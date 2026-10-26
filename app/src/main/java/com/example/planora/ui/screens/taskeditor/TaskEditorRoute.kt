package com.example.planora.ui.screens.taskeditor

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun TaskEditorRoute(
    onNavigateBack: () -> Unit,
    viewModel: TaskEditorViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val availableCategories by viewModel.availableCategories.collectAsStateWithLifecycle()

    LaunchedEffect(uiState.isSaved) {
        if (uiState.isSaved) {
            onNavigateBack()
        }
    }

    TaskEditorScreen(
        uiState = uiState,
        availableCategories = availableCategories,
        onNavigateBack = onNavigateBack,
        onTitleChange = viewModel::updateTitle,
        onDescriptionChange = viewModel::updateDescription,
        onDueDateChange = viewModel::updateDueDate,
        onDueTimeChange = viewModel::updateDueTime,
        onPriorityChange = viewModel::updatePriority,
        onCategoryChange = viewModel::updateCategory,
        onRecurrenceChange = viewModel::updateRecurrence,
        onNotesChange = viewModel::updateNotes,
        onPinnedChange = viewModel::updatePinned,
        onAddSubtask = viewModel::addSubtask,
        onToggleSubtask = viewModel::toggleSubtask,
        onDeleteSubtask = viewModel::deleteSubtask,
        onSaveTask = viewModel::saveTask
    )
}
