package com.example.planora.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "task_templates")
data class TaskTemplateEntity(
    @PrimaryKey
    val id: String = UUID.randomUUID().toString(),
    val title: String,
    val description: String = "",
    val priority: String = "NONE",
    val categoryId: String? = null,
    val relativeDueDays: Int = 0,
    val colorHex: String? = null,
    val defaultSubtasksJson: String = "[]"
)
