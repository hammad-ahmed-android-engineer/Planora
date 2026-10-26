package com.example.planora.ui.screens.templates

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.planora.domain.model.Category
import com.example.planora.domain.model.Priority
import com.example.planora.domain.model.TaskTemplate
import com.example.planora.domain.usecase.ManageCategoriesUseCase
import com.example.planora.domain.usecase.ManageTemplatesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

data class TemplateEditorUiState(
    val templateId: String? = null,
    val title: String = "",
    val description: String = "",
    val selectedPriority: Priority = Priority.NONE,
    val selectedCategoryId: String? = null,
    val relativeDueDays: Int = 0,
    val subtasks: List<String> = emptyList(),
    val newSubtaskText: String = "",
    val isEdit: Boolean = false,
    val isSaved: Boolean = false
)

@HiltViewModel
class TemplateEditorViewModel @Inject constructor(
    private val manageTemplatesUseCase: ManageTemplatesUseCase,
    manageCategoriesUseCase: ManageCategoriesUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val templateId: String? = savedStateHandle.get<String>("templateId")

    private val _uiState = MutableStateFlow(
        TemplateEditorUiState(
            templateId = templateId,
            isEdit = !templateId.isNullOrBlank()
        )
    )
    val uiState: StateFlow<TemplateEditorUiState> = _uiState.asStateFlow()

    val categories: StateFlow<List<Category>> = manageCategoriesUseCase.getAllCategories()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    init {
        if (!templateId.isNullOrBlank()) {
            viewModelScope.launch {
                val allTemplates = manageTemplatesUseCase.getAllTemplates().first()
                allTemplates.find { it.id == templateId }?.let { template ->
                    _uiState.update { current ->
                        current.copy(
                            title = template.title,
                            description = template.description,
                            selectedPriority = template.priority,
                            selectedCategoryId = template.categoryId,
                            relativeDueDays = template.relativeDueDays,
                            subtasks = template.defaultSubtasks,
                            isEdit = true
                        )
                    }
                }
            }
        }
    }

    fun updateTitle(title: String) { _uiState.update { it.copy(title = title) } }
    fun updateDescription(desc: String) { _uiState.update { it.copy(description = desc) } }
    fun updatePriority(priority: Priority) { _uiState.update { it.copy(selectedPriority = priority) } }
    fun updateCategory(categoryId: String?) { _uiState.update { it.copy(selectedCategoryId = categoryId) } }
    fun updateRelativeDueDays(days: Int) { _uiState.update { it.copy(relativeDueDays = days) } }
    fun updateNewSubtaskText(text: String) { _uiState.update { it.copy(newSubtaskText = text) } }

    fun addSubtask() {
        val text = _uiState.value.newSubtaskText.trim()
        if (text.isNotBlank()) {
            _uiState.update { it.copy(subtasks = it.subtasks + text, newSubtaskText = "") }
        }
    }

    fun deleteSubtask(index: Int) {
        _uiState.update { it.copy(subtasks = it.subtasks.filterIndexed { i, _ -> i != index }) }
    }

    fun saveTemplate() {
        val current = _uiState.value
        if (current.title.isBlank()) return

        viewModelScope.launch {
            val template = TaskTemplate(
                id = current.templateId ?: UUID.randomUUID().toString(),
                title = current.title.trim(),
                description = current.description.trim(),
                priority = current.selectedPriority,
                categoryId = current.selectedCategoryId,
                relativeDueDays = current.relativeDueDays,
                defaultSubtasks = current.subtasks
            )
            manageTemplatesUseCase.saveTemplate(template, current.isEdit)
            _uiState.update { it.copy(isSaved = true) }
        }
    }
}
