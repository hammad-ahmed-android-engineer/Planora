package com.example.planora.presentation.calendar

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.planora.domain.model.Task
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
import java.time.YearMonth
import javax.inject.Inject

data class CalendarUiState(
    val selectedDate: LocalDate = LocalDate.now(),
    val currentMonth: YearMonth = YearMonth.now(),
    val tasksForSelectedDate: List<Task> = emptyList(),
    val allActiveTasks: List<Task> = emptyList()
)

@HiltViewModel
class CalendarViewModel @Inject constructor(
    getTasksUseCase: GetTasksUseCase,
    private val toggleTaskCompletionUseCase: ToggleTaskCompletionUseCase,
    private val saveTaskUseCase: SaveTaskUseCase,
    private val deleteTaskUseCase: DeleteTaskUseCase,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    companion object {
        private const val KEY_SELECTED_DATE = "calendar_selected_date"
        private const val KEY_CURRENT_MONTH = "calendar_current_month"
    }

    private val selectedDateFlow = savedStateHandle.getStateFlow(KEY_SELECTED_DATE, LocalDate.now().toString())
    private val currentMonthFlow = savedStateHandle.getStateFlow(KEY_CURRENT_MONTH, YearMonth.now().toString())

    val uiState: StateFlow<CalendarUiState> = combine(
        selectedDateFlow,
        currentMonthFlow,
        getTasksUseCase.getAllActiveTasks()
    ) { dateStr, monthStr, tasks ->
        val date = try { LocalDate.parse(dateStr) } catch (_: Exception) { LocalDate.now() }
        val month = try { YearMonth.parse(monthStr) } catch (_: Exception) { YearMonth.now() }
        val activeTasks = tasks.filter { !it.isDeleted && !it.isArchived }
        val tasksForDate = activeTasks.filter { it.dueDate == date.toString() }

        CalendarUiState(
            selectedDate = date,
            currentMonth = month,
            tasksForSelectedDate = tasksForDate,
            allActiveTasks = activeTasks
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = CalendarUiState()
    )

    fun setSelectedDate(date: LocalDate) {
        savedStateHandle[KEY_SELECTED_DATE] = date.toString()
        savedStateHandle[KEY_CURRENT_MONTH] = YearMonth.from(date).toString()
    }

    fun setCurrentMonth(month: YearMonth) {
        savedStateHandle[KEY_CURRENT_MONTH] = month.toString()
    }

    fun goToToday() {
        val today = LocalDate.now()
        savedStateHandle[KEY_SELECTED_DATE] = today.toString()
        savedStateHandle[KEY_CURRENT_MONTH] = YearMonth.from(today).toString()
    }

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
}
