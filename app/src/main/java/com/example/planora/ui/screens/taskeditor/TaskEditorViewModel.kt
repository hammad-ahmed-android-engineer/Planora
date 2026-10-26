package com.example.planora.ui.screens.taskeditor

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.planora.domain.model.Category
import com.example.planora.domain.model.Priority
import com.example.planora.domain.model.RecurrenceType
import com.example.planora.domain.model.Subtask
import com.example.planora.domain.model.Tag
import com.example.planora.domain.model.Task
import com.example.planora.domain.usecase.GetTasksUseCase
import com.example.planora.domain.usecase.ManageCategoriesUseCase
import com.example.planora.domain.usecase.ManageTagsUseCase
import com.example.planora.domain.usecase.SaveTaskUseCase
import com.example.planora.util.isValidTitle
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.util.UUID
import javax.inject.Inject

data class TaskEditorUiState(
    val taskId: String? = null,
    val title: String = "",
    val description: String = "",
    val dueDate: String? = LocalDate.now().toString(),
    val dueTime: String? = null,
    val priority: Priority = Priority.NONE,
    val selectedCategory: Category? = null,
    val selectedTags: List<Tag> = emptyList(),
    val subtasks: List<Subtask> = emptyList(),
    val recurrenceType: RecurrenceType = RecurrenceType.NONE,
    val recurrenceInterval: Int = 1,
    val estimatedDurationMinutes: Int = 30,
    val colorHex: String? = null,
    val notes: String = "",
    val isPinned: Boolean = false,
    val isSaved: Boolean = false,
    val isEditMode: Boolean = false
)

@HiltViewModel
class TaskEditorViewModel @Inject constructor(
    private val getTasksUseCase: GetTasksUseCase,
    manageCategoriesUseCase: ManageCategoriesUseCase,
    manageTagsUseCase: ManageTagsUseCase,
    private val saveTaskUseCase: SaveTaskUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val taskId: String? = savedStateHandle.get<String>("taskId")

    private val _existingTask = MutableStateFlow<Task?>(null)

    private val _uiState = MutableStateFlow(
        TaskEditorUiState(
            taskId = taskId,
            isEditMode = !taskId.isNullOrBlank()
        )
    )
    val uiState: StateFlow<TaskEditorUiState> = _uiState.asStateFlow()

    val availableCategories: StateFlow<List<Category>> = manageCategoriesUseCase.getAllCategories()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val availableTags: StateFlow<List<Tag>> = manageTagsUseCase.getAllTags()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    init {
        if (!taskId.isNullOrBlank()) {
            viewModelScope.launch {
                val task = getTasksUseCase.getTaskById(taskId).first()
                task?.let { t ->
                    _existingTask.value = t
                    _uiState.update { current ->
                        current.copy(
                            title = t.title,
                            description = t.description,
                            dueDate = t.dueDate,
                            dueTime = t.dueTime,
                            priority = t.priority,
                            selectedCategory = t.category,
                            selectedTags = t.tags,
                            subtasks = t.subtasks,
                            recurrenceType = t.recurrenceType,
                            recurrenceInterval = t.recurrenceInterval,
                            estimatedDurationMinutes = t.estimatedDurationMinutes,
                            colorHex = t.colorHex,
                            notes = t.notes,
                            isPinned = t.isPinned
                        )
                    }
                }
            }
        }
    }

    fun updateTitle(title: String) { _uiState.update { it.copy(title = title) } }
    fun updateDescription(desc: String) { _uiState.update { it.copy(description = desc) } }
    fun updateDueDate(date: String?) { _uiState.update { it.copy(dueDate = date) } }
    fun updateDueTime(time: String?) { _uiState.update { it.copy(dueTime = time) } }
    fun updatePriority(priority: Priority) { _uiState.update { it.copy(priority = priority) } }
    fun updateCategory(category: Category?) { _uiState.update { it.copy(selectedCategory = category) } }
    fun updateRecurrence(type: RecurrenceType) { _uiState.update { it.copy(recurrenceType = type) } }
    fun updateNotes(notes: String) { _uiState.update { it.copy(notes = notes) } }
    fun updatePinned(isPinned: Boolean) { _uiState.update { it.copy(isPinned = isPinned) } }

    fun addSubtask(title: String) {
        val newSub = Subtask(title = title, sortOrder = _uiState.value.subtasks.size)
        _uiState.update { it.copy(subtasks = it.subtasks + newSub) }
    }

    fun toggleSubtask(subtask: Subtask) {
        val updated = _uiState.value.subtasks.map {
            if (it.id == subtask.id) it.copy(isCompleted = !it.isCompleted) else it
        }
        _uiState.update { it.copy(subtasks = updated) }
    }

    fun deleteSubtask(subtask: Subtask) {
        _uiState.update { it.copy(subtasks = it.subtasks.filter { s -> s.id != subtask.id }) }
    }

    fun saveTask() {
        val state = _uiState.value
        if (!state.title.isValidTitle()) return

        viewModelScope.launch {
            val baseTask = _existingTask.value
            val task = baseTask?.copy(
                title = state.title.trim(),
                description = state.description.trim(),
                dueDate = state.dueDate,
                dueTime = state.dueTime,
                priority = state.priority,
                category = state.selectedCategory,
                tags = state.selectedTags,
                subtasks = state.subtasks,
                recurrenceType = state.recurrenceType,
                recurrenceInterval = state.recurrenceInterval,
                estimatedDurationMinutes = state.estimatedDurationMinutes,
                colorHex = state.colorHex,
                notes = state.notes.trim(),
                isPinned = state.isPinned,
                lastModifiedTimestamp = System.currentTimeMillis()
            ) ?: Task(
                id = taskId ?: UUID.randomUUID().toString(),
                title = state.title.trim(),
                description = state.description.trim(),
                dueDate = state.dueDate,
                dueTime = state.dueTime,
                priority = state.priority,
                category = state.selectedCategory,
                tags = state.selectedTags,
                subtasks = state.subtasks,
                recurrenceType = state.recurrenceType,
                recurrenceInterval = state.recurrenceInterval,
                estimatedDurationMinutes = state.estimatedDurationMinutes,
                colorHex = state.colorHex,
                notes = state.notes.trim(),
                isPinned = state.isPinned
            )

            saveTaskUseCase(task, isNewTask = !state.isEditMode)
            _uiState.update { it.copy(isSaved = true) }
        }
    }
}
