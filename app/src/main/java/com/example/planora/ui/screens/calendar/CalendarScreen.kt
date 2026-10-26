package com.example.planora.ui.screens.calendar

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import com.example.planora.domain.model.Task
import com.example.planora.ui.screens.calendar.components.CalendarAgendaTaskList
import com.example.planora.ui.screens.calendar.components.CalendarDayAgendaHeader
import com.example.planora.ui.screens.calendar.components.CalendarMonthCard
import com.example.planora.ui.screens.calendar.components.CalendarTopAppBar
import com.example.planora.ui.theme.dimensions
import java.time.LocalDate
import java.time.YearMonth

@Composable
fun CalendarScreen(
    uiState: CalendarUiState,
    onNavigateToTaskDetail: (taskId: String) -> Unit,
    onNavigateToTaskEditor: (taskId: String) -> Unit = {},
    onDateSelected: (LocalDate) -> Unit,
    onMonthChange: (YearMonth) -> Unit,
    onGoToToday: () -> Unit,
    onToggleComplete: (Task) -> Unit,
    onToggleFavorite: (Task) -> Unit,
    onTogglePin: (Task) -> Unit,
    onDeleteTask: (Task) -> Unit
) {
    val dims = dimensions
    val isLandscape = LocalConfiguration.current.orientation == Configuration.ORIENTATION_LANDSCAPE

    val taskDateMap = remember(uiState.allActiveTasks) {
        uiState.allActiveTasks.groupBy { it.dueDate }
    }

    Scaffold(
        topBar = {
            CalendarTopAppBar(onGoToToday = onGoToToday)
        }
    ) { paddingValues ->
        if (isLandscape) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(dims.spacing.default)
            ) {
                Column(
                    modifier = Modifier
                        .weight(1.1f)
                        .padding(end = dims.spacing.medium)
                ) {
                    CalendarMonthCard(
                        currentMonth = uiState.currentMonth,
                        onPrevMonth = { onMonthChange(uiState.currentMonth.minusMonths(1)) },
                        onNextMonth = { onMonthChange(uiState.currentMonth.plusMonths(1)) },
                        selectedDate = uiState.selectedDate,
                        onDateSelected = onDateSelected,
                        taskDateMap = taskDateMap
                    )
                }

                Column(
                    modifier = Modifier
                        .weight(1.2f)
                        .fillMaxSize()
                ) {
                    CalendarDayAgendaHeader(
                        selectedDate = uiState.selectedDate,
                        taskCount = uiState.tasksForSelectedDate.size
                    )

                    Spacer(modifier = Modifier.height(dims.spacing.small))

                    CalendarAgendaTaskList(
                        tasks = uiState.tasksForSelectedDate,
                        onTaskClick = onNavigateToTaskEditor,
                        onEditTask = onNavigateToTaskEditor,
                        onToggleComplete = onToggleComplete,
                        onToggleFavorite = onToggleFavorite,
                        onTogglePin = onTogglePin,
                        onDeleteTask = onDeleteTask,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(horizontal = dims.spacing.medium)
            ) {
                CalendarMonthCard(
                    currentMonth = uiState.currentMonth,
                    onPrevMonth = { onMonthChange(uiState.currentMonth.minusMonths(1)) },
                    onNextMonth = { onMonthChange(uiState.currentMonth.plusMonths(1)) },
                    selectedDate = uiState.selectedDate,
                    onDateSelected = onDateSelected,
                    taskDateMap = taskDateMap
                )

                Spacer(modifier = Modifier.height(dims.spacing.medium))

                CalendarDayAgendaHeader(
                    selectedDate = uiState.selectedDate,
                    taskCount = uiState.tasksForSelectedDate.size
                )

                Spacer(modifier = Modifier.height(dims.spacing.small))

                CalendarAgendaTaskList(
                    tasks = uiState.tasksForSelectedDate,
                    onTaskClick = onNavigateToTaskEditor,
                    onEditTask = onNavigateToTaskEditor,
                    onToggleComplete = onToggleComplete,
                    onToggleFavorite = onToggleFavorite,
                    onTogglePin = onTogglePin,
                    onDeleteTask = onDeleteTask,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                )
            }
        }
    }
}
