package com.example.planora.presentation.navigation

sealed class Screen(val route: String) {
    data object Onboarding : Screen("onboarding")
    data object Home : Screen("home")
    data object TaskView : Screen("task_view/{viewType}") {
        fun createRoute(viewType: String) = "task_view/$viewType"
    }
    data object TaskDetail : Screen("task_detail/{taskId}") {
        fun createRoute(taskId: String) = "task_detail/$taskId"
    }
    data object TaskEditor : Screen("task_editor?taskId={taskId}") {
        fun createRoute(taskId: String? = null) = if (taskId != null) "task_editor?taskId=$taskId" else "task_editor"
    }
    data object Calendar : Screen("calendar")
    data object Categories : Screen("categories")
    data object Search : Screen("search")
    data object Focus : Screen("focus")
    data object Stats : Screen("stats")
    data object Templates : Screen("templates")
    data object TemplateEditor : Screen("template_editor?templateId={templateId}") {
        fun createRoute(templateId: String? = null) = if (templateId != null) "template_editor?templateId=$templateId" else "template_editor"
    }
    data object Settings : Screen("settings")
    data object Backup : Screen("backup")
}
