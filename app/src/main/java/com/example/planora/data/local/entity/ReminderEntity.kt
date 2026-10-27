package com.example.planora.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.planora.domain.model.Reminder
import java.util.UUID

@Entity(
    tableName = "reminders",
    foreignKeys = [
        ForeignKey(
            entity = TaskEntity::class,
            parentColumns = ["id"],
            childColumns = ["taskId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("taskId")]
)
data class ReminderEntity(
    @PrimaryKey
    val id: String = UUID.randomUUID().toString(),
    val taskId: String,
    val triggerTimestamp: Long, // epoch millis
    val leadTimeMinutes: Int = 0,
    val isEnabled: Boolean = true,
    val notificationId: Int = (System.currentTimeMillis() % Int.MAX_VALUE).toInt()
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
