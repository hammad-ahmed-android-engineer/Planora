package com.example.planora.presentation.taskdetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.planora.domain.model.Subtask
import com.example.planora.domain.model.Task
import com.example.planora.domain.usecase.DeleteTaskUseCase
import com.example.planora.domain.usecase.GetTasksUseCase
import com.example.planora.domain.usecase.SaveTaskUseCase
import com.example.planora.domain.usecase.ToggleTaskCompletionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

data class TaskDetailUiState(
    val task: Task? = null,
    val isLoading: Boolean = true
)

@HiltViewModel
class TaskDetailViewModel @Inject constructor(
    private val toggleTaskCompletionUseCase: ToggleTaskCompletionUseCase,
    private val saveTaskUseCase: SaveTaskUseCase,
    private val deleteTaskUseCase: DeleteTaskUseCase,
    getTasksUseCase: GetTasksUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    val taskId: String = savedStateHandle.get<String>("taskId") ?: ""

    val uiState: StateFlow<TaskDetailUiState> = getTasksUseCase.getTaskById(taskId)
        .map { task -> TaskDetailUiState(task = task, isLoading = false) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = TaskDetailUiState(isLoading = true)
        )

    fun toggleComplete() {
        val currentTask = uiState.value.task ?: return
        viewModelScope.launch {
            toggleTaskCompletionUseCase(currentTask, !currentTask.isCompleted)
        }
    }

    fun toggleFavorite() {
        val currentTask = uiState.value.task ?: return
        viewModelScope.launch {
            saveTaskUseCase(currentTask.copy(isStarred = !currentTask.isStarred))
        }
    }

    fun togglePin() {
        val currentTask = uiState.value.task ?: return
        viewModelScope.launch {
            saveTaskUseCase.toggleTaskPin(currentTask)
        }
    }

    fun toggleSubtask(subtask: Subtask) {
        val currentTask = uiState.value.task ?: return
        val updatedSubtasks = currentTask.subtasks.map {
            if (it.id == subtask.id) it.copy(isCompleted = !it.isCompleted) else it
        }
        viewModelScope.launch {
            saveTaskUseCase(currentTask.copy(subtasks = updatedSubtasks))
        }
    }

    fun postponeToTomorrow() {
        val currentTask = uiState.value.task ?: return
        val tomorrow = LocalDate.now().plusDays(1).toString()
        viewModelScope.launch {
            saveTaskUseCase(currentTask.copy(dueDate = tomorrow))
        }
    }

    fun deleteTask() {
        val currentTask = uiState.value.task ?: return
        viewModelScope.launch {
            deleteTaskUseCase(currentTask)
        }
    }
}
