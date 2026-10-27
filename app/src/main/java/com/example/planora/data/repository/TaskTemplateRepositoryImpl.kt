package com.example.planora.data.repository

import com.example.planora.data.local.dao.TaskTemplateDao
import com.example.planora.data.local.entity.TaskTemplateEntity
import com.example.planora.data.local.entity.toDomain
import com.example.planora.data.local.entity.toEntity
import com.example.planora.domain.model.TaskTemplate
import com.example.planora.domain.repository.TaskTemplateRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

import javax.inject.Inject

class TaskTemplateRepositoryImpl @Inject constructor(
    private val templateDao: TaskTemplateDao
) : TaskTemplateRepository {

    override fun getAllTemplates(): Flow<List<TaskTemplate>> {
        return templateDao.getAllTemplates().map { list -> list.map { it.toDomain() } }
    }

    override suspend fun insertTemplate(template: TaskTemplate) {
        templateDao.insertTemplate(template.toEntity())
    }

    override suspend fun updateTemplate(template: TaskTemplate) {
        templateDao.updateTemplate(template.toEntity())
    }

    override suspend fun deleteTemplate(template: TaskTemplate) {
        templateDao.deleteTemplate(template.toEntity())
    }

    override suspend fun seedDefaultTemplatesIfEmpty() {
        val existing = templateDao.getAllTemplatesDirect()
        if (existing.isEmpty()) {
            val defaultTemplates = listOf(
                TaskTemplateEntity(
                    id = "tpl_study",
                    title = "Study Session",
                    description = "Review material and practice problems",
                    priority = "MEDIUM",
                    categoryId = "cat_study",
                    relativeDueDays = 0,
                    defaultSubtasksJson = "[\"Read assigned chapter\",\"Summarize key points\",\"Solve practice problems\"]"
                ),
                TaskTemplateEntity(
                    id = "tpl_shopping",
                    title = "Grocery Shopping",
                    description = "Weekly grocery store trip",
                    priority = "LOW",
                    categoryId = "cat_shopping",
                    relativeDueDays = 1,
                    defaultSubtasksJson = "[\"Fresh produce\",\"Dairy & eggs\",\"Pantry essentials\"]"
                ),
                TaskTemplateEntity(
                    id = "tpl_weekly_review",
                    title = "Weekly Productivity Review",
                    description = "Reflect on completed work and plan upcoming tasks",
                    priority = "HIGH",
                    categoryId = "cat_personal",
                    relativeDueDays = 7,
                    defaultSubtasksJson = "[\"Review completed tasks\",\"Clear inbox\",\"Set priorities for next week\"]"
                )
            )
            templateDao.insertTemplates(defaultTemplates)
        }
    }
}
