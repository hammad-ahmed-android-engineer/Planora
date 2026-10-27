package com.example.planora.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.planora.domain.model.Task
import java.util.UUID

@Entity(
    tableName = "tasks",
    foreignKeys = [
        ForeignKey(
            entity = CategoryEntity::class,
            parentColumns = ["id"],
            childColumns = ["categoryId"],
            onDelete = ForeignKey.SET_NULL
        )
    ],
    indices = [
        Index("categoryId"),
        Index("dueDate"),
        Index("isCompleted"),
        Index("isDeleted"),
        Index("isArchived")
    ]
)
data class TaskEntity(
    @PrimaryKey
    val id: String = UUID.randomUUID().toString(),
    val title: String,
    val description: String = "",
    val createdTimestamp: Long = System.currentTimeMillis(),
    val lastModifiedTimestamp: Long = System.currentTimeMillis(),
    val dueDate: String? = null, // ISO-8601 YYYY-MM-DD
    val dueTime: String? = null, // ISO-8601 HH:mm
    val startDate: String? = null, // ISO-8601 YYYY-MM-DD
    val startTime: String? = null, // ISO-8601 HH:mm
    val deadline: Long? = null, // Epoch millis
    val priority: String = "NONE", // NONE, LOW, MEDIUM, HIGH, URGENT
    val categoryId: String? = null,
    val isStarred: Boolean = false,
    val isPinned: Boolean = false,
    val colorHex: String? = null,
    val estimatedDurationMinutes: Int = 0,
    val actualTimeSpentMinutes: Int = 0,
    val completionTimestamp: Long? = null,
    val isCompleted: Boolean = false,
    val isArchived: Boolean = false,
    val isDeleted: Boolean = false, // Trash status
    val recurrenceType: String = "NONE", // NONE, DAILY, WEEKDAYS, WEEKENDS, WEEKLY, MONTHLY, YEARLY, CUSTOM
    val recurrenceInterval: Int = 1,
    val recurrenceDaysOfWeek: String? = null, // e.g., "1,3,5" (Mon, Wed, Fri)
    val recurrenceEndTimestamp: Long? = null,
    val repeatAfterCompletion: Boolean = false,
    val locationReminder: String? = null,
    val parentTaskId: String? = null,
    val templateId: String? = null,
    val notes: String = ""
)

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
