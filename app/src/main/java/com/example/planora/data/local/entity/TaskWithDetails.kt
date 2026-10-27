package com.example.planora.data.local.entity

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.example.planora.domain.model.Priority
import com.example.planora.domain.model.RecurrenceType
import com.example.planora.domain.model.Task

data class TaskWithDetails(
    @Embedded
    val task: TaskEntity,

    @Relation(
        parentColumn = "categoryId",
        entityColumn = "id"
    )
    val category: CategoryEntity?,

    @Relation(
        parentColumn = "id",
        entityColumn = "taskId"
    )
    val subtasks: List<SubtaskEntity> = emptyList(),

    @Relation(
        parentColumn = "id",
        entityColumn = "taskId"
    )
    val reminders: List<ReminderEntity> = emptyList(),

    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(
            value = TaskTagCrossRef::class,
            parentColumn = "taskId",
            entityColumn = "tagId"
        )
    )
    val tags: List<TagEntity> = emptyList()
)

fun TaskWithDetails.toDomain(): Task {
    return Task(
        id = task.id,
        title = task.title,
        description = task.description,
        createdTimestamp = task.createdTimestamp,
        lastModifiedTimestamp = task.lastModifiedTimestamp,
        dueDate = task.dueDate,
        dueTime = task.dueTime,
        startDate = task.startDate,
        startTime = task.startTime,
        deadline = task.deadline,
        priority = Priority.fromString(task.priority),
        category = category?.toDomain(),
        tags = tags.map { it.toDomain() },
        subtasks = subtasks.sortedBy { it.sortOrder }.map { it.toDomain() },
        reminders = reminders.map { it.toDomain() },
        isStarred = task.isStarred,
        isPinned = task.isPinned,
        colorHex = task.colorHex,
        estimatedDurationMinutes = task.estimatedDurationMinutes,
        actualTimeSpentMinutes = task.actualTimeSpentMinutes,
        completionTimestamp = task.completionTimestamp,
        isCompleted = task.isCompleted,
        isArchived = task.isArchived,
        isDeleted = task.isDeleted,
        recurrenceType = RecurrenceType.fromString(task.recurrenceType),
        recurrenceInterval = task.recurrenceInterval,
        recurrenceDaysOfWeek = task.recurrenceDaysOfWeek,
        recurrenceEndTimestamp = task.recurrenceEndTimestamp,
        repeatAfterCompletion = task.repeatAfterCompletion,
        locationReminder = task.locationReminder,
        parentTaskId = task.parentTaskId,
        templateId = task.templateId,
        notes = task.notes
    )
}
