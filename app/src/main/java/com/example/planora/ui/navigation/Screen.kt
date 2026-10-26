package com.example.planora.ui.navigation

sealed class Screen(val route: String) {
    object Onboarding : Screen("onboarding")
    object Home : Screen("home")
    object TaskView : Screen("task_view/{viewType}") {
        fun createRoute(viewType: String) = "task_view/$viewType"
    }
    object TaskDetail : Screen("task_detail/{taskId}") {
        fun createRoute(taskId: String) = "task_detail/$taskId"
    }
    object TaskEditor : Screen("task_editor?taskId={taskId}") {
        fun createRoute(taskId: String? = null) = if (taskId != null) "task_editor?taskId=$taskId" else "task_editor"
    }
    object Calendar : Screen("calendar")
    object Categories : Screen("categories")
    object Search : Screen("search")
    object Focus : Screen("focus")
    object Stats : Screen("stats")
    object Templates : Screen("templates")
    object TemplateEditor : Screen("template_editor?templateId={templateId}") {
        fun createRoute(templateId: String? = null) = if (templateId != null) "template_editor?templateId=$templateId" else "template_editor"
    }
    object Settings : Screen("settings")
    object Backup : Screen("backup")
}
