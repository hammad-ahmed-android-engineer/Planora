package com.example.planora.presentation.templates

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun TemplatesRoute(
    onNavigateBack: () -> Unit,
    onNavigateToTaskDetail: (taskId: String) -> Unit,
    onNavigateToTemplateEditor: (templateId: String?) -> Unit,
    viewModel: TemplatesViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    TemplatesScreen(
        uiState = uiState,
        onNavigateBack = onNavigateBack,
        onNavigateToTaskDetail = onNavigateToTaskDetail,
        onNavigateToTemplateEditor = onNavigateToTemplateEditor,
        onUseTemplate = viewModel::createTaskFromTemplate,
        onDeleteTemplate = viewModel::deleteTemplate
    )
}
