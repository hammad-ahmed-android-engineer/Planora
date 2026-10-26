package com.example.planora.ui.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.planora.ui.screens.calendar.CalendarRoute
import com.example.planora.ui.screens.categories.CategoryRoute
import com.example.planora.ui.screens.focus.FocusTimerRoute
import com.example.planora.ui.screens.home.HomeRoute
import com.example.planora.ui.screens.search.SearchFilterRoute
import com.example.planora.ui.screens.settings.SettingsRoute
import com.example.planora.ui.screens.stats.StatsRoute
import com.example.planora.ui.screens.taskdetail.TaskDetailRoute
import com.example.planora.ui.screens.taskeditor.TaskEditorRoute
import com.example.planora.ui.screens.taskview.TaskViewRoute
import com.example.planora.ui.screens.templates.TemplateEditorRoute
import com.example.planora.ui.screens.templates.TemplatesRoute

@Composable
fun NavHostContainer(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route,
        modifier = modifier,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None },
        popEnterTransition = { EnterTransition.None },
        popExitTransition = { ExitTransition.None }
    ) {
        composable(Screen.Home.route) {
            HomeRoute(
                onNavigateToTaskEditor = { taskId ->
                    navController.navigate(Screen.TaskEditor.createRoute(taskId))
                },
                onNavigateToTaskDetail = { taskId ->
                    navController.navigate(Screen.TaskDetail.createRoute(taskId))
                }
            )
        }

        composable(
            route = Screen.TaskView.route,
            arguments = listOf(navArgument("viewType") { type = NavType.StringType })
        ) {
            TaskViewRoute(
                onNavigateBack = { navController.popBackStack() },
                onNavigateToTaskDetail = { taskId ->
                    navController.navigate(Screen.TaskDetail.createRoute(taskId))
                },
                onNavigateToTaskEditor = { taskId ->
                    navController.navigate(Screen.TaskEditor.createRoute(taskId))
                }
            )
        }

        composable(
            route = Screen.TaskDetail.route,
            arguments = listOf(navArgument("taskId") { type = NavType.StringType })
        ) {
            TaskDetailRoute(
                onNavigateBack = { navController.popBackStack() },
                onNavigateToEdit = { taskId ->
                    navController.navigate(Screen.TaskEditor.createRoute(taskId))
                }
            )
        }

        composable(
            route = Screen.TaskEditor.route,
            arguments = listOf(navArgument("taskId") {
                type = NavType.StringType
                nullable = true
                defaultValue = null
            })
        ) {
            TaskEditorRoute(
                onNavigateBack = { navController.popBackStack() }
            )
        }

        composable(Screen.Calendar.route) {
            CalendarRoute(
                onNavigateToTaskDetail = { taskId ->
                    navController.navigate(Screen.TaskDetail.createRoute(taskId))
                },
                onNavigateToTaskEditor = { taskId ->
                    navController.navigate(Screen.TaskEditor.createRoute(taskId))
                }
            )
        }

        composable(Screen.Categories.route) {
            CategoryRoute(
                onNavigateBack = { navController.popBackStack() }
            )
        }

        composable(Screen.Search.route) {
            SearchFilterRoute(
                onNavigateToTaskDetail = { taskId ->
                    navController.navigate(Screen.TaskDetail.createRoute(taskId))
                },
                onNavigateToTaskEditor = { taskId ->
                    navController.navigate(Screen.TaskEditor.createRoute(taskId))
                }
            )
        }

        composable(Screen.Focus.route) {
            FocusTimerRoute()
        }

        composable(Screen.Stats.route) {
            StatsRoute()
        }

        composable(Screen.Templates.route) {
            TemplatesRoute(
                onNavigateBack = { navController.popBackStack() },
                onNavigateToTaskDetail = { taskId ->
                    navController.navigate(Screen.TaskDetail.createRoute(taskId))
                },
                onNavigateToTemplateEditor = { templateId ->
                    navController.navigate(Screen.TemplateEditor.createRoute(templateId))
                }
            )
        }

        composable(
            route = Screen.TemplateEditor.route,
            arguments = listOf(navArgument("templateId") {
                type = NavType.StringType
                nullable = true
                defaultValue = null
            })
        ) {
            TemplateEditorRoute(
                onNavigateBack = { navController.popBackStack() }
            )
        }

        composable(Screen.Settings.route) {
            SettingsRoute(
                onNavigateToCategories = { navController.navigate(Screen.Categories.route) },
                onNavigateToTemplates = { navController.navigate(Screen.Templates.route) },
                onNavigateToStats = { navController.navigate(Screen.Stats.route) },
                onNavigateToArchived = { navController.navigate(Screen.TaskView.createRoute("ARCHIVED")) },
                onNavigateToTrash = { navController.navigate(Screen.TaskView.createRoute("TRASH")) }
            )
        }
    }
}
