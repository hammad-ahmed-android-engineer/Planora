package com.example.planora.presentation.templates

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import com.example.planora.R
import com.example.planora.core.designsystem.components.AppButton
import com.example.planora.core.designsystem.components.AppOutlinedTextField
import com.example.planora.core.designsystem.theme.AppTypography
import com.example.planora.core.designsystem.theme.dimensions
import com.example.planora.core.utils.rememberDebouncedClick
import com.example.planora.domain.model.Category
import com.example.planora.domain.model.Priority
import com.example.planora.presentation.templates.components.TemplateOrganizationCard
import com.example.planora.presentation.templates.components.TemplateSubtasksSection

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TemplateEditorScreen(
    uiState: TemplateEditorUiState,
    categoriesList: List<Category>,
    onNavigateBack: () -> Unit,
    onTitleChange: (String) -> Unit,
    onDescriptionChange: (String) -> Unit,
    onPriorityChange: (Priority) -> Unit,
    onCategoryChange: (String?) -> Unit,
    onRelativeDueDaysChange: (Int) -> Unit,
    onNewSubtaskTextChange: (String) -> Unit,
    onAddSubtask: () -> Unit,
    onDeleteSubtask: (Int) -> Unit,
    onSaveTemplate: () -> Unit
) {
    val dims = dimensions
    val primaryColor = MaterialTheme.colorScheme.primary
    val isLandscape = LocalConfiguration.current.orientation == Configuration.ORIENTATION_LANDSCAPE
    var showCategoryMenu by rememberSaveable { mutableStateOf(false) }

    val debouncedBack = rememberDebouncedClick(onClick = onNavigateBack)
    val debouncedSave = rememberDebouncedClick(onClick = onSaveTemplate)

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    AppTypography.TitleLarge(
                        text = if (uiState.isEdit) stringResource(R.string.templates_edit_title) else stringResource(R.string.templates_new_title)
                    )
                },
                navigationIcon = {
                    IconButton(onClick = debouncedBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.back),
                            tint = primaryColor
                        )
                    }
                },
                actions = {
                    AppButton(
                        text = stringResource(R.string.save),
                        onClick = debouncedSave,
                        icon = Icons.Default.Check,
                        enabled = uiState.title.isNotBlank(),
                        containerColor = primaryColor
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        }
    ) { paddingValues ->
        if (isLandscape) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .imePadding()
                    .padding(dims.spacing.default)
            ) {
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .verticalScroll(rememberScrollState())
                        .padding(end = dims.spacing.medium)
                ) {
                    AppOutlinedTextField(
                        value = uiState.title,
                        onValueChange = onTitleChange,
                        label = stringResource(R.string.templates_name_label),
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(dims.spacing.medium))

                    AppOutlinedTextField(
                        value = uiState.description,
                        onValueChange = onDescriptionChange,
                        label = stringResource(R.string.task_editor_desc_hint),
                        singleLine = false,
                        maxLines = 5,
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                Column(
                    modifier = Modifier
                        .weight(1.3f)
                        .verticalScroll(rememberScrollState())
                ) {
                    TemplateOrganizationCard(
                        selectedCategoryId = uiState.selectedCategoryId,
                        categoriesList = categoriesList,
                        selectedPriority = uiState.selectedPriority,
                        relativeDueDays = uiState.relativeDueDays,
                        showCategoryMenu = showCategoryMenu,
                        onToggleCategoryMenu = { showCategoryMenu = it },
                        onSelectCategory = onCategoryChange,
                        onSelectPriority = onPriorityChange,
                        onSelectRelativeDays = onRelativeDueDaysChange
                    )

                    Spacer(modifier = Modifier.height(dims.spacing.default))

                    TemplateSubtasksSection(
                        subtasks = uiState.subtasks,
                        newSubtaskText = uiState.newSubtaskText,
                        onNewSubtaskChange = onNewSubtaskTextChange,
                        onAddSubtask = onAddSubtask,
                        onDeleteSubtask = onDeleteSubtask
                    )
                }
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .imePadding()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = dims.spacing.default, vertical = dims.spacing.small)
            ) {
                AppOutlinedTextField(
                    value = uiState.title,
                    onValueChange = onTitleChange,
                    label = stringResource(R.string.templates_name_label),
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(dims.spacing.medium))

                AppOutlinedTextField(
                    value = uiState.description,
                    onValueChange = onDescriptionChange,
                    label = stringResource(R.string.task_editor_desc_hint),
                    singleLine = false,
                    maxLines = 3,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(dims.spacing.default))

                TemplateOrganizationCard(
                    selectedCategoryId = uiState.selectedCategoryId,
                    categoriesList = categoriesList,
                    selectedPriority = uiState.selectedPriority,
                    relativeDueDays = uiState.relativeDueDays,
                    showCategoryMenu = showCategoryMenu,
                    onToggleCategoryMenu = { showCategoryMenu = it },
                    onSelectCategory = onCategoryChange,
                    onSelectPriority = onPriorityChange,
                    onSelectRelativeDays = onRelativeDueDaysChange
                )

                Spacer(modifier = Modifier.height(dims.spacing.default))

                TemplateSubtasksSection(
                    subtasks = uiState.subtasks,
                    newSubtaskText = uiState.newSubtaskText,
                    onNewSubtaskChange = onNewSubtaskTextChange,
                    onAddSubtask = onAddSubtask,
                    onDeleteSubtask = onDeleteSubtask
                )

                Spacer(modifier = Modifier.height(dims.spacing.large))
            }
        }
    }
}
