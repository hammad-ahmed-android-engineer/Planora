package com.example.planora.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.planora.domain.model.Priority
import com.example.planora.domain.model.TaskTemplate
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.UUID

private val templateGson = Gson()

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

fun TaskTemplateEntity.toDomain(): TaskTemplate {
    val listType = object : TypeToken<List<String>>() {}.type
    val subtasksList: List<String> = try {
        templateGson.fromJson(defaultSubtasksJson, listType) ?: emptyList()
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
    defaultSubtasksJson = templateGson.toJson(defaultSubtasks)
)
