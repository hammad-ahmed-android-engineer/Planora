package com.example.planora.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.planora.domain.model.Priority
import com.example.planora.domain.model.Task
import com.example.planora.domain.model.TaskViewType
import com.example.planora.domain.usecase.ManagePreferencesUseCase
import com.example.planora.domain.usecase.DeleteTaskUseCase
import com.example.planora.domain.usecase.GetTasksUseCase
import com.example.planora.domain.usecase.SaveTaskUseCase
import com.example.planora.domain.usecase.ToggleTaskCompletionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

data class HomeUiState(
    val todayTasks: List<Task> = emptyList(),
    val pinnedTasks: List<Task> = emptyList(),
    val overdueTasks: List<Task> = emptyList(),
    val activeCount: Int = 0,
    val completedCount: Int = 0,
    val overdueCount: Int = 0,
    val todayCount: Int = 0,
    val userName: String = "User",
    val currentDateStr: String = LocalDate.now().toString(),
    val currentStreak: Int = 0,
    val notificationStylePrompted: Boolean = true,
    val notificationStyle: String = "NOTIFICATION"
)

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val toggleTaskCompletionUseCase: ToggleTaskCompletionUseCase,
    private val saveTaskUseCase: SaveTaskUseCase,
    private val deleteTaskUseCase: DeleteTaskUseCase,
    private val getTasksUseCase: GetTasksUseCase,
    private val managePreferencesUseCase: ManagePreferencesUseCase
) : ViewModel() {

    val uiState: StateFlow<HomeUiState> = combine(
        combine(
            getTasksUseCase.getSmartListTasks(TaskViewType.TODAY),
            getTasksUseCase.getSmartListTasks(TaskViewType.PINNED),
            getTasksUseCase.getSmartListTasks(TaskViewType.OVERDUE)
        ) { today, pinned, overdue -> Triple(today, pinned, overdue) },
        combine(
            getTasksUseCase.getActiveTaskCount(),
            getTasksUseCase.getCompletedTaskCount(),
            managePreferencesUseCase.userSettingsFlow
        ) { activeCnt, completedCnt, settings -> Triple(activeCnt, completedCnt, settings) }
    ) { (today, pinned, overdue), (activeCnt, completedCnt, settings) ->
        HomeUiState(
            todayTasks = today,
            pinnedTasks = pinned,
            overdueTasks = overdue,
            activeCount = activeCnt,
            completedCount = completedCnt,
            overdueCount = overdue.size,
            todayCount = today.size,
            currentDateStr = LocalDate.now().toString(),
            currentStreak = settings.currentStreak,
            notificationStylePrompted = settings.notificationStylePrompted,
            notificationStyle = settings.notificationStyle
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = HomeUiState()
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

    fun selectNotificationStyle(style: String) {
        viewModelScope.launch {
            managePreferencesUseCase.updateNotificationStyle(style)
            managePreferencesUseCase.updateNotificationStylePrompted(true)
        }
    }

    fun quickAddTask(title: String, dueDate: String?, dueTime: String?, priorityName: String, tags: List<String>) {
        viewModelScope.launch {
            val priority = Priority.fromString(priorityName)
            val newTask = Task(
                title = title,
                dueDate = dueDate ?: LocalDate.now().toString(),
                dueTime = dueTime,
                priority = priority
            )
            saveTaskUseCase(newTask, isNewTask = true)
        }
    }

    fun deleteTask(task: Task) {
        viewModelScope.launch {
            deleteTaskUseCase(task)
        }
    }

    fun postponeTaskToTomorrow(task: Task) {
        viewModelScope.launch {
            val tomorrow = LocalDate.now().plusDays(1).toString()
            saveTaskUseCase(task.copy(dueDate = tomorrow))
        }
    }
}
