package com.example.planora.domain.usecase

import com.example.planora.domain.model.RecurrenceType
import com.example.planora.domain.model.Task
import com.example.planora.domain.repository.TaskRepository
import com.example.planora.notification.AlarmScheduler
import javax.inject.Inject

/**
 * UseCase for toggling task completion status.
 * Handles recurring tasks by automatically calculating the next due date and rescheduling alarms.
 */
class ToggleTaskCompletionUseCase @Inject constructor(
    private val taskRepository: TaskRepository,
    private val alarmScheduler: AlarmScheduler
) {
    /**
     * Toggles or sets the completion state of task.
     *
     * @param task Target task to toggle
     * @param isCompleted Target completion state
     */
    suspend operator fun invoke(task: Task, isCompleted: Boolean) {
        if (isCompleted && task.recurrenceType != RecurrenceType.NONE) {
            val nextDueDate = RecurrenceCalculator.calculateNextDueDate(task)
            if (nextDueDate != null) {
                // Update task for next recurrence cycle
                val updatedTask = task.copy(
                    dueDate = nextDueDate,
                    isCompleted = false,
                    completionTimestamp = null,
                    lastModifiedTimestamp = System.currentTimeMillis()
                )
                taskRepository.updateTask(updatedTask)
                alarmScheduler.scheduleTaskReminder(updatedTask)
                return
            }
        }

        // Standard completion toggle
        val updatedTask = task.copy(
            isCompleted = isCompleted,
            completionTimestamp = if (isCompleted) System.currentTimeMillis() else null,
            lastModifiedTimestamp = System.currentTimeMillis()
        )
        taskRepository.completeTask(updatedTask, isCompleted)

        if (isCompleted) {
            alarmScheduler.cancelTaskReminder(task.id)
        } else {
            alarmScheduler.scheduleTaskReminder(updatedTask)
        }
    }
}
