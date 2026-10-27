package com.example.planora.presentation.taskeditor

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import com.example.planora.domain.model.Category
import com.example.planora.domain.model.Priority
import com.example.planora.domain.model.RecurrenceType
import com.example.planora.domain.model.Subtask
import com.example.planora.core.designsystem.components.CustomDatePickerDialog
import com.example.planora.core.designsystem.components.CustomTimePickerDialog
import com.example.planora.core.designsystem.components.SubtaskChecklistEditor
import com.example.planora.presentation.taskeditor.components.OrganizationSectionCard
import com.example.planora.presentation.taskeditor.components.RecurrenceDropdownMenu
import com.example.planora.presentation.taskeditor.components.ScheduleSectionCard
import com.example.planora.presentation.taskeditor.components.TaskDescriptionField
import com.example.planora.presentation.taskeditor.components.TaskEditorTopAppBar
import com.example.planora.presentation.taskeditor.components.TaskNotesField
import com.example.planora.presentation.taskeditor.components.TaskTitleField
import com.example.planora.core.designsystem.theme.dimensions

@Composable
fun TaskEditorScreen(
    uiState: TaskEditorUiState,
    availableCategories: List<Category>,
    onNavigateBack: () -> Unit,
    onTitleChange: (String) -> Unit,
    onDescriptionChange: (String) -> Unit,
    onDueDateChange: (String?) -> Unit,
    onDueTimeChange: (String?) -> Unit,
    onPriorityChange: (Priority) -> Unit,
    onCategoryChange: (Category?) -> Unit,
    onRecurrenceChange: (RecurrenceType) -> Unit,
    onNotesChange: (String) -> Unit,
    onPinnedChange: (Boolean) -> Unit,
    onAddSubtask: (String) -> Unit,
    onToggleSubtask: (Subtask) -> Unit,
    onDeleteSubtask: (Subtask) -> Unit,
    onSaveTask: () -> Unit
) {
    val dims = dimensions
    val isLandscape = LocalConfiguration.current.orientation == Configuration.ORIENTATION_LANDSCAPE

    var showDatePicker by rememberSaveable { mutableStateOf(false) }
    var showTimePicker by rememberSaveable { mutableStateOf(false) }
    var showCategoryMenu by rememberSaveable { mutableStateOf(false) }
    var showRecurrenceMenu by rememberSaveable { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TaskEditorTopAppBar(
                isEditMode = uiState.isEditMode,
                canSave = uiState.title.isNotBlank(),
                onNavigateBack = onNavigateBack,
                onSaveTask = onSaveTask
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
                    TaskTitleField(title = uiState.title, onTitleChange = onTitleChange)

                    Spacer(modifier = Modifier.height(dims.spacing.medium))

                    TaskDescriptionField(
                        description = uiState.description,
                        onDescriptionChange = onDescriptionChange,
                        minLines = 3,
                        maxLines = 5
                    )

                    Spacer(modifier = Modifier.height(dims.spacing.default))

                    ScheduleSectionCard(
                        dueDate = uiState.dueDate,
                        dueTime = uiState.dueTime,
                        recurrenceType = uiState.recurrenceType,
                        recurrenceInterval = uiState.recurrenceInterval,
                        onOpenDatePicker = { showDatePicker = true },
                        onOpenTimePicker = { showTimePicker = true },
                        onOpenRecurrenceMenu = { showRecurrenceMenu = true }
                    )
                }

                Column(
                    modifier = Modifier
                        .weight(1.3f)
                        .verticalScroll(rememberScrollState())
                        .padding(start = dims.spacing.medium)
                ) {
                    OrganizationSectionCard(
                        selectedCategory = uiState.selectedCategory,
                        availableCategories = availableCategories,
                        selectedPriority = uiState.priority,
                        isPinned = uiState.isPinned,
                        onSelectCategory = onCategoryChange,
                        onSelectPriority = onPriorityChange,
                        onTogglePinned = onPinnedChange,
                        showCategoryMenu = showCategoryMenu,
                        onToggleCategoryMenu = { showCategoryMenu = it }
                    )

                    Spacer(modifier = Modifier.height(dims.spacing.default))

                    SubtaskChecklistEditor(
                        subtasks = uiState.subtasks,
                        onAddSubtask = onAddSubtask,
                        onToggleSubtask = onToggleSubtask,
                        onDeleteSubtask = onDeleteSubtask
                    )

                    Spacer(modifier = Modifier.height(dims.spacing.default))

                    TaskNotesField(notes = uiState.notes, onNotesChange = onNotesChange)
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
                TaskTitleField(title = uiState.title, onTitleChange = onTitleChange)

                Spacer(modifier = Modifier.height(dims.spacing.medium))

                TaskDescriptionField(
                    description = uiState.description,
                    onDescriptionChange = onDescriptionChange,
                    minLines = 2,
                    maxLines = 4
                )

                Spacer(modifier = Modifier.height(dims.spacing.default))

                ScheduleSectionCard(
                    dueDate = uiState.dueDate,
                    dueTime = uiState.dueTime,
                    recurrenceType = uiState.recurrenceType,
                    recurrenceInterval = uiState.recurrenceInterval,
                    onOpenDatePicker = { showDatePicker = true },
                    onOpenTimePicker = { showTimePicker = true },
                    onOpenRecurrenceMenu = { showRecurrenceMenu = true }
                )

                Spacer(modifier = Modifier.height(dims.spacing.default))

                OrganizationSectionCard(
                    selectedCategory = uiState.selectedCategory,
                    availableCategories = availableCategories,
                    selectedPriority = uiState.priority,
                    isPinned = uiState.isPinned,
                    onSelectCategory = onCategoryChange,
                    onSelectPriority = onPriorityChange,
                    onTogglePinned = onPinnedChange,
                    showCategoryMenu = showCategoryMenu,
                    onToggleCategoryMenu = { showCategoryMenu = it }
                )

                Spacer(modifier = Modifier.height(dims.spacing.default))

                SubtaskChecklistEditor(
                    subtasks = uiState.subtasks,
                    onAddSubtask = onAddSubtask,
                    onToggleSubtask = onToggleSubtask,
                    onDeleteSubtask = onDeleteSubtask
                )

                Spacer(modifier = Modifier.height(dims.spacing.default))

                TaskNotesField(notes = uiState.notes, onNotesChange = onNotesChange)

                Spacer(modifier = Modifier.height(dims.spacing.large))
            }
        }

        if (showDatePicker) {
            CustomDatePickerDialog(
                onDateSelected = { onDueDateChange(it); showDatePicker = false },
                onDismiss = { showDatePicker = false }
            )
        }

        if (showTimePicker) {
            CustomTimePickerDialog(
                onTimeSelected = { onDueTimeChange(it); showTimePicker = false },
                onDismiss = { showTimePicker = false }
            )
        }

        RecurrenceDropdownMenu(
            expanded = showRecurrenceMenu,
            onDismissRequest = { showRecurrenceMenu = false },
            onRecurrenceSelected = onRecurrenceChange
        )
    }
}
