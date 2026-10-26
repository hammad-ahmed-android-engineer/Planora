package com.example.planora.domain.usecase

import com.example.planora.domain.model.TaskTemplate
import com.example.planora.domain.repository.TaskTemplateRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ManageTemplatesUseCase @Inject constructor(
    private val templateRepository: TaskTemplateRepository
) {
    fun getAllTemplates(): Flow<List<TaskTemplate>> {
        return templateRepository.getAllTemplates()
    }

    suspend fun saveTemplate(template: TaskTemplate, isEdit: Boolean) {
        if (isEdit) {
            templateRepository.updateTemplate(template)
        } else {
            templateRepository.insertTemplate(template)
        }
    }

    suspend fun deleteTemplate(template: TaskTemplate) {
        templateRepository.deleteTemplate(template)
    }

    suspend fun seedDefaultTemplatesIfEmpty() {
        templateRepository.seedDefaultTemplatesIfEmpty()
    }
}
