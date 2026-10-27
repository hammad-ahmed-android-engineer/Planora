package com.example.planora.domain.usecase

import com.example.planora.core.notification.AlarmScheduler
import com.example.planora.domain.model.Task
import com.example.planora.domain.repository.TaskRepository
import javax.inject.Inject

/**
 * UseCase responsible for saving (inserting or updating) a task.
 * Also handles scheduling or rescheduling alarm reminders.
 */
class SaveTaskUseCase @Inject constructor(
    private val taskRepository: TaskRepository,
    private val alarmScheduler: AlarmScheduler
) {
    /**
     * Saves the given task and updates associated notification alarms.
     *
     * @param task Task entity to be saved
     * @param isNewTask True if inserting a new task, false if updating an existing one
     */
    suspend operator fun invoke(task: Task, isNewTask: Boolean = false) {
        if (isNewTask) {
            taskRepository.insertTask(task)
        } else {
            taskRepository.updateTask(task)
        }

        if (task.isCompleted || task.isDeleted) {
            alarmScheduler.cancelTaskReminder(task.id)
        } else {
            alarmScheduler.scheduleTaskReminder(task)
        }
    }

    suspend fun toggleTaskPin(task: Task) {
        taskRepository.toggleTaskPin(task)
    }
}
