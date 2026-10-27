package com.example.planora.presentation.templates

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun TemplateEditorRoute(
    onNavigateBack: () -> Unit,
    viewModel: TemplateEditorViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val categoriesList by viewModel.categories.collectAsStateWithLifecycle()

    LaunchedEffect(uiState.isSaved) {
        if (uiState.isSaved) {
            onNavigateBack()
        }
    }

    TemplateEditorScreen(
        uiState = uiState,
        categoriesList = categoriesList,
        onNavigateBack = onNavigateBack,
        onTitleChange = viewModel::updateTitle,
        onDescriptionChange = viewModel::updateDescription,
        onPriorityChange = viewModel::updatePriority,
        onCategoryChange = viewModel::updateCategory,
        onRelativeDueDaysChange = viewModel::updateRelativeDueDays,
        onNewSubtaskTextChange = viewModel::updateNewSubtaskText,
        onAddSubtask = viewModel::addSubtask,
        onDeleteSubtask = viewModel::deleteSubtask,
        onSaveTemplate = viewModel::saveTemplate
    )
}
