package com.example.planora.domain.usecase

import com.example.planora.domain.model.Task
import com.example.planora.domain.repository.TaskRepository
import com.example.planora.notification.AlarmScheduler
import javax.inject.Inject

/**
 * UseCase for deleting tasks and clearing associated reminders.
 */
class DeleteTaskUseCase @Inject constructor(
    private val taskRepository: TaskRepository,
    private val alarmScheduler: AlarmScheduler
) {
    /**
     * Deletes the specified task and cancels its reminder alarm.
     */
    suspend operator fun invoke(task: Task) {
        alarmScheduler.cancelTaskReminder(task.id)
        taskRepository.deleteTask(task)
    }

    /**
     * Moves list of task IDs to trash.
     */
    suspend fun moveToTrash(taskIds: List<String>) {
        taskIds.forEach { taskId ->
            alarmScheduler.cancelTaskReminder(taskId)
        }
        taskRepository.moveTasksToTrash(taskIds)
    }

    /**
     * Restores list of task IDs from trash.
     */
    suspend fun restoreFromTrash(taskIds: List<String>) {
        taskRepository.restoreTasksFromTrash(taskIds)
    }

    /**
     * Permanently empties trash.
     */
    suspend fun emptyTrash() {
        taskRepository.emptyTrash()
    }
}
