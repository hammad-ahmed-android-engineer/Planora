package com.example.planora.presentation.templates

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import com.example.planora.R
import com.example.planora.domain.model.TaskTemplate
import com.example.planora.core.designsystem.components.ConfirmationDialog
import com.example.planora.presentation.templates.components.TemplatesContent
import com.example.planora.presentation.templates.components.TemplatesFloatingActionButton
import com.example.planora.presentation.templates.components.TemplatesTopAppBar
import com.example.planora.core.utils.rememberDebouncedClick
import kotlinx.coroutines.launch

@Composable
fun TemplatesScreen(
    uiState: TemplatesUiState,
    onNavigateBack: () -> Unit,
    onNavigateToTaskDetail: (taskId: String) -> Unit,
    onNavigateToTemplateEditor: (templateId: String?) -> Unit,
    onUseTemplate: (TaskTemplate, (String, String) -> Unit) -> Unit,
    onDeleteTemplate: (TaskTemplate) -> Unit
) {
    val isLandscape = LocalConfiguration.current.orientation == Configuration.ORIENTATION_LANDSCAPE
    val templatesList = uiState.templates
    val categoriesList = uiState.categories

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    var deletingTemplateId by rememberSaveable { mutableStateOf<String?>(null) }
    val deletingTemplate = remember(deletingTemplateId, templatesList) {
        templatesList.find { it.id == deletingTemplateId }
    }

    val debouncedCreate = rememberDebouncedClick { onNavigateToTemplateEditor(null) }

    Scaffold(
        topBar = {
            TemplatesTopAppBar(onNavigateBack = onNavigateBack)
        },
        floatingActionButton = {
            TemplatesFloatingActionButton(onCreateTemplate = debouncedCreate)
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            val taskCreatedTemplate = stringResource(R.string.templates_task_created_msg)
            val viewTaskLabel = stringResource(R.string.templates_view_task)

            TemplatesContent(
                templatesList = templatesList,
                categoriesList = categoriesList,
                isLoading = uiState.isLoading,
                isLandscape = isLandscape,
                onCreateTemplate = debouncedCreate,
                onUseTemplate = { tpl ->
                    onUseTemplate(tpl) { taskId, _ ->
                        scope.launch {
                            val result = snackbarHostState.showSnackbar(
                                message = String.format(taskCreatedTemplate, tpl.title),
                                actionLabel = viewTaskLabel,
                                duration = SnackbarDuration.Short
                            )
                            if (result == SnackbarResult.ActionPerformed) {
                                onNavigateToTaskDetail(taskId)
                            }
                        }
                    }
                },
                onEditTemplate = { tpl -> onNavigateToTemplateEditor(tpl.id) },
                onDeleteTemplate = { tpl -> deletingTemplateId = tpl.id }
            )
        }

        deletingTemplate?.let { tpl ->
            ConfirmationDialog(
                title = stringResource(R.string.templates_delete_title),
                message = stringResource(R.string.templates_delete_msg, tpl.title),
                confirmText = stringResource(R.string.delete),
                onConfirm = {
                    onDeleteTemplate(tpl)
                    deletingTemplateId = null
                },
                onDismiss = { deletingTemplateId = null }
            )
        }
    }
}
