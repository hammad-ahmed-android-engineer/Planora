package com.example.planora.presentation.templates

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.planora.domain.model.Category
import com.example.planora.domain.model.Subtask
import com.example.planora.domain.model.Task
import com.example.planora.domain.model.TaskTemplate
import com.example.planora.domain.usecase.ManageCategoriesUseCase
import com.example.planora.domain.usecase.ManageTemplatesUseCase
import com.example.planora.domain.usecase.SaveTaskUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.util.UUID
import javax.inject.Inject

data class TemplatesUiState(
    val templates: List<TaskTemplate> = emptyList(),
    val categories: List<Category> = emptyList(),
    val isLoading: Boolean = false
)

@HiltViewModel
class TemplatesViewModel @Inject constructor(
    private val manageTemplatesUseCase: ManageTemplatesUseCase,
    private val saveTaskUseCase: SaveTaskUseCase,
    private val manageCategoriesUseCase: ManageCategoriesUseCase
) : ViewModel() {

    val uiState: StateFlow<TemplatesUiState> = combine(
        manageTemplatesUseCase.getAllTemplates(),
        manageCategoriesUseCase.getAllCategories()
    ) { templates, categories ->
        TemplatesUiState(
            templates = templates,
            categories = categories,
            isLoading = false
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = TemplatesUiState(isLoading = true)
    )

    init {
        viewModelScope.launch {
            manageTemplatesUseCase.seedDefaultTemplatesIfEmpty()
        }
    }

    fun saveTemplate(template: TaskTemplate, isEdit: Boolean) {
        viewModelScope.launch {
            manageTemplatesUseCase.saveTemplate(template, isEdit)
        }
    }

    fun deleteTemplate(template: TaskTemplate) {
        viewModelScope.launch {
            manageTemplatesUseCase.deleteTemplate(template)
        }
    }

    fun createTaskFromTemplate(template: TaskTemplate, onTaskCreated: (taskId: String, title: String) -> Unit) {
        viewModelScope.launch {
            val due = LocalDate.now().plusDays(template.relativeDueDays.toLong()).toString()
            val taskId = UUID.randomUUID().toString()

            val subtasks = template.defaultSubtasks.mapIndexed { index, subTitle ->
                Subtask(
                    id = UUID.randomUUID().toString(),
                    taskId = taskId,
                    title = subTitle,
                    sortOrder = index
                )
            }

            val category = uiState.value.categories.find { it.id == template.categoryId }

            val newTask = Task(
                id = taskId,
                title = template.title,
                description = template.description,
                priority = template.priority,
                category = category,
                dueDate = due,
                subtasks = subtasks,
                templateId = template.id
            )

            saveTaskUseCase(newTask, isNewTask = true)
            onTaskCreated(taskId, template.title)
        }
    }
}
