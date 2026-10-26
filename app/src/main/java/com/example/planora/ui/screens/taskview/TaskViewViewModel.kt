package com.example.planora.ui.screens.taskview

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.planora.domain.model.Task
import com.example.planora.domain.model.TaskViewType
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
import javax.inject.Inject

data class TaskViewUiState(
    val viewType: TaskViewType = TaskViewType.TODAY,
    val tasks: List<Task> = emptyList()
)

@HiltViewModel
class TaskViewViewModel @Inject constructor(
    getTasksUseCase: GetTasksUseCase,
    private val toggleTaskCompletionUseCase: ToggleTaskCompletionUseCase,
    private val saveTaskUseCase: SaveTaskUseCase,
    private val deleteTaskUseCase: DeleteTaskUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val viewTypeStr: String = savedStateHandle.get<String>("viewType") ?: "TODAY"
    val viewType: TaskViewType = try {
        TaskViewType.valueOf(viewTypeStr.uppercase())
    } catch (_: Exception) {
        TaskViewType.TODAY
    }

    val uiState: StateFlow<TaskViewUiState> = getTasksUseCase.getSmartListTasks(viewType)
        .map { taskList -> TaskViewUiState(viewType = viewType, tasks = taskList) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = TaskViewUiState(viewType = viewType)
        )

    fun toggleTaskComplete(task: Task) {
        viewModelScope.launch {
            toggleTaskCompletionUseCase(task, !task.isCompleted)
        }
    }

    fun toggleTaskFavorite(task: Task) {
        viewModelScope.launch {
            saveTaskUseCase(task.copy(isStarred = !task.isStarred))
        }
    }

    fun toggleTaskPin(task: Task) {
        viewModelScope.launch {
            saveTaskUseCase.toggleTaskPin(task)
        }
    }

    fun deleteTask(task: Task) {
        viewModelScope.launch {
            deleteTaskUseCase(task)
        }
    }

    fun restoreTaskFromTrash(task: Task) {
        viewModelScope.launch {
            deleteTaskUseCase.restoreFromTrash(listOf(task.id))
        }
    }

    fun unarchiveTask(task: Task) {
        viewModelScope.launch {
            saveTaskUseCase(task.copy(isArchived = false, lastModifiedTimestamp = System.currentTimeMillis()))
        }
    }

    fun emptyTrash() {
        viewModelScope.launch {
            deleteTaskUseCase.emptyTrash()
        }
    }
}
