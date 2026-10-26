package com.example.planora.data.mapper

import com.example.planora.data.local.entity.CategoryEntity
import com.example.planora.data.local.entity.FocusSessionEntity
import com.example.planora.data.local.entity.ReminderEntity
import com.example.planora.data.local.entity.SubtaskEntity
import com.example.planora.data.local.entity.TagEntity
import com.example.planora.data.local.entity.TaskEntity
import com.example.planora.data.local.entity.TaskTemplateEntity
import com.example.planora.data.local.entity.TaskWithDetails
import com.example.planora.domain.model.Category
import com.example.planora.domain.model.FocusSession
import com.example.planora.domain.model.Priority
import com.example.planora.domain.model.RecurrenceType
import com.example.planora.domain.model.Reminder
import com.example.planora.domain.model.Subtask
import com.example.planora.domain.model.Tag
import com.example.planora.domain.model.Task
import com.example.planora.domain.model.TaskTemplate
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

private val gson = Gson()

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

fun Task.toEntity(): TaskEntity {
    return TaskEntity(
        id = id,
        title = title,
        description = description,
        createdTimestamp = createdTimestamp,
        lastModifiedTimestamp = lastModifiedTimestamp,
        dueDate = dueDate,
        dueTime = dueTime,
        startDate = startDate,
        startTime = startTime,
        deadline = deadline,
        priority = priority.name,
        categoryId = category?.id,
        isStarred = isStarred,
        isPinned = isPinned,
        colorHex = colorHex,
        estimatedDurationMinutes = estimatedDurationMinutes,
        actualTimeSpentMinutes = actualTimeSpentMinutes,
        completionTimestamp = completionTimestamp,
        isCompleted = isCompleted,
        isArchived = isArchived,
        isDeleted = isDeleted,
        recurrenceType = recurrenceType.name,
        recurrenceInterval = recurrenceInterval,
        recurrenceDaysOfWeek = recurrenceDaysOfWeek,
        recurrenceEndTimestamp = recurrenceEndTimestamp,
        repeatAfterCompletion = repeatAfterCompletion,
        locationReminder = locationReminder,
        parentTaskId = parentTaskId,
        templateId = templateId,
        notes = notes
    )
}

fun SubtaskEntity.toDomain() = Subtask(
    id = id,
    taskId = taskId,
    title = title,
    isCompleted = isCompleted,
    sortOrder = sortOrder
)

fun Subtask.toEntity(taskId: String) = SubtaskEntity(
    id = id,
    taskId = taskId,
    title = title,
    isCompleted = isCompleted,
    sortOrder = sortOrder
)

fun CategoryEntity.toDomain() = Category(
    id = id,
    name = name,
    description = description,
    colorHex = colorHex,
    iconName = iconName,
    createdTimestamp = createdTimestamp,
    isArchived = isArchived,
    deadline = deadline
)

fun Category.toEntity() = CategoryEntity(
    id = id,
    name = name,
    description = description,
    colorHex = colorHex,
    iconName = iconName,
    createdTimestamp = createdTimestamp,
    isArchived = isArchived,
    deadline = deadline
)

fun TagEntity.toDomain() = Tag(
    id = id,
    name = name,
    colorHex = colorHex
)

fun Tag.toEntity() = TagEntity(
    id = id,
    name = name,
    colorHex = colorHex
)

fun ReminderEntity.toDomain() = Reminder(
    id = id,
    taskId = taskId,
    triggerTimestamp = triggerTimestamp,
    leadTimeMinutes = leadTimeMinutes,
    isEnabled = isEnabled,
    notificationId = notificationId
)

fun Reminder.toEntity(taskId: String) = ReminderEntity(
    id = id,
    taskId = taskId,
    triggerTimestamp = triggerTimestamp,
    leadTimeMinutes = leadTimeMinutes,
    isEnabled = isEnabled,
    notificationId = notificationId
)

fun TaskTemplateEntity.toDomain(): TaskTemplate {
    val listType = object : TypeToken<List<String>>() {}.type
    val subtasksList: List<String> = try {
        gson.fromJson(defaultSubtasksJson, listType) ?: emptyList()
    } catch (e: Exception) {
        emptyList()
    }
    return TaskTemplate(
        id = id,
        title = title,
        description = description,
        priority = Priority.fromString(priority),
        categoryId = categoryId,
        relativeDueDays = relativeDueDays,
        colorHex = colorHex,
        defaultSubtasks = subtasksList
    )
}

fun TaskTemplate.toEntity() = TaskTemplateEntity(
    id = id,
    title = title,
    description = description,
    priority = priority.name,
    categoryId = categoryId,
    relativeDueDays = relativeDueDays,
    colorHex = colorHex,
    defaultSubtasksJson = gson.toJson(defaultSubtasks)
)

fun FocusSessionEntity.toDomain() = FocusSession(
    id = id,
    taskId = taskId,
    startTimestamp = startTimestamp,
    endTimestamp = endTimestamp,
    durationMinutes = durationMinutes,
    sessionType = sessionType,
    isCompleted = isCompleted
)

fun FocusSession.toEntity() = FocusSessionEntity(
    id = id,
    taskId = taskId,
    startTimestamp = startTimestamp,
    endTimestamp = endTimestamp,
    durationMinutes = durationMinutes,
    sessionType = sessionType,
    isCompleted = isCompleted
)
