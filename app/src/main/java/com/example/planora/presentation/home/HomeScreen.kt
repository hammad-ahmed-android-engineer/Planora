package com.example.planora.presentation.home

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import com.example.planora.R
import com.example.planora.core.designsystem.components.AppFloatingActionButton
import com.example.planora.core.designsystem.theme.dimensions
import com.example.planora.core.utils.greetingForTimeOfDay
import com.example.planora.core.utils.rememberDebouncedClick
import com.example.planora.domain.model.Task
import com.example.planora.presentation.home.components.DailyProgressHeroCard
import com.example.planora.presentation.home.components.FilterTabsRow
import com.example.planora.presentation.home.components.GreetingHeroHeader
import com.example.planora.presentation.home.components.NotificationStylePromptDialog
import com.example.planora.presentation.home.components.StatsOverviewGrid
import com.example.planora.presentation.home.components.TaskListContent
import java.time.LocalDate
import java.time.format.DateTimeFormatter

enum class HomeFilterTab {
    TODAY, PINNED, OVERDUE, ALL
}

@Composable
fun HomeScreen(
    uiState: HomeUiState,
    onNavigateToTaskEditor: (taskId: String?) -> Unit,
    onNavigateToTaskDetail: (taskId: String) -> Unit,
    onToggleComplete: (Task) -> Unit,
    onToggleFavorite: (Task) -> Unit,
    onTogglePin: (Task) -> Unit,
    onDeleteTask: (Task) -> Unit,
    onPostponeTask: (Task) -> Unit,
    onSelectNotificationStyle: (String) -> Unit
) {
    val dims = dimensions
    val isLandscape = LocalConfiguration.current.orientation == Configuration.ORIENTATION_LANDSCAPE
    var selectedTab by rememberSaveable { mutableStateOf(HomeFilterTab.TODAY) }

    val formattedDate = remember {
        try {
            LocalDate.now().format(DateTimeFormatter.ofPattern("EEEE, MMM d"))
        } catch (_: Exception) {
            uiState.currentDateStr
        }
    }

    val greeting = remember { greetingForTimeOfDay() }
    val debouncedCreateTask = rememberDebouncedClick { onNavigateToTaskEditor(null) }

    Scaffold(
        floatingActionButton = {
            AppFloatingActionButton(
                onClick = debouncedCreateTask,
                icon = Icons.Default.Add,
                contentDescription = stringResource(R.string.home_add_task),
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = Color.White,
                shape = RoundedCornerShape(dims.corners.large)
            )
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
                        .verticalScroll(rememberScrollState())
                        .padding(end = dims.spacing.medium)
                ) {
                    GreetingHeroHeader(
                        greeting = greeting,
                        dateStr = formattedDate,
                        streak = uiState.currentStreak
                    )

                    Spacer(modifier = Modifier.height(dims.spacing.medium))

                    DailyProgressHeroCard(
                        todayTasks = uiState.todayTasks,
                        activeCount = uiState.activeCount,
                        completedCount = uiState.completedCount
                    )

                    Spacer(modifier = Modifier.height(dims.spacing.medium))

                    StatsOverviewGrid(
                        todayCount = uiState.todayCount,
                        completedCount = uiState.completedCount,
                        overdueCount = uiState.overdueCount
                    )
                }

                Column(
                    modifier = Modifier
                        .weight(1.5f)
                        .fillMaxSize()
                ) {
                    FilterTabsRow(
                        selectedTab = selectedTab,
                        onTabSelected = { selectedTab = it },
                        todayCount = uiState.todayTasks.size,
                        pinnedCount = uiState.pinnedTasks.size,
                        overdueCount = uiState.overdueTasks.size,
                        allCount = uiState.activeCount
                    )

                    Spacer(modifier = Modifier.height(dims.spacing.small))

                    TaskListContent(
                        selectedTab = selectedTab,
                        uiState = uiState,
                        onNavigateToTaskEditor = onNavigateToTaskEditor,
                        onNavigateToTaskDetail = onNavigateToTaskDetail,
                        onToggleComplete = onToggleComplete,
                        onToggleFavorite = onToggleFavorite,
                        onTogglePin = onTogglePin,
                        onDeleteTask = onDeleteTask,
                        onPostponeTask = onPostponeTask,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                GreetingHeroHeader(
                    greeting = greeting,
                    dateStr = formattedDate,
                    streak = uiState.currentStreak,
                    modifier = Modifier.padding(horizontal = dims.spacing.default, vertical = dims.spacing.small)
                )

                DailyProgressHeroCard(
                    todayTasks = uiState.todayTasks,
                    activeCount = uiState.activeCount,
                    completedCount = uiState.completedCount,
                    modifier = Modifier.padding(horizontal = dims.spacing.default, vertical = dims.spacing.micro)
                )

                Spacer(modifier = Modifier.height(dims.spacing.small))

                FilterTabsRow(
                    selectedTab = selectedTab,
                    onTabSelected = { selectedTab = it },
                    todayCount = uiState.todayTasks.size,
                    pinnedCount = uiState.pinnedTasks.size,
                    overdueCount = uiState.overdueTasks.size,
                    allCount = uiState.activeCount
                )

                Spacer(modifier = Modifier.height(dims.spacing.tiny))

                TaskListContent(
                    selectedTab = selectedTab,
                    uiState = uiState,
                    onNavigateToTaskEditor = onNavigateToTaskEditor,
                    onNavigateToTaskDetail = onNavigateToTaskDetail,
                    onToggleComplete = onToggleComplete,
                    onToggleFavorite = onToggleFavorite,
                    onTogglePin = onTogglePin,
                    onDeleteTask = onDeleteTask,
                    onPostponeTask = onPostponeTask,
                    modifier = Modifier.weight(1f)
                )
            }
        }

        if (!uiState.notificationStylePrompted) {
            NotificationStylePromptDialog(
                currentStyle = uiState.notificationStyle,
                onStyleSelected = onSelectNotificationStyle
            )
        }
    }
}
