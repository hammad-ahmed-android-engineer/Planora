package com.example.planora.domain.repository

import com.example.planora.domain.model.TaskTemplate
import kotlinx.coroutines.flow.Flow

interface TaskTemplateRepository {
    fun getAllTemplates(): Flow<List<TaskTemplate>>
    suspend fun insertTemplate(template: TaskTemplate)
    suspend fun updateTemplate(template: TaskTemplate)
    suspend fun deleteTemplate(template: TaskTemplate)
    suspend fun seedDefaultTemplatesIfEmpty()
}
