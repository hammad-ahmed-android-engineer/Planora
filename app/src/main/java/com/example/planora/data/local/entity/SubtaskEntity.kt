package com.example.planora.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.planora.domain.model.Subtask
import java.util.UUID

@Entity(
    tableName = "subtasks",
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
data class SubtaskEntity(
    @PrimaryKey
    val id: String = UUID.randomUUID().toString(),
    val taskId: String,
    val title: String,
    val isCompleted: Boolean = false,
    val sortOrder: Int = 0
)

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
