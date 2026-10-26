package com.example.planora.domain.usecase

import com.example.planora.domain.model.Tag
import com.example.planora.domain.repository.TagRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ManageTagsUseCase @Inject constructor(
    private val tagRepository: TagRepository
) {
    fun getAllTags(): Flow<List<Tag>> {
        return tagRepository.getAllTags()
    }

    suspend fun addTag(tag: Tag) {
        tagRepository.insertTag(tag)
    }

    suspend fun updateTag(tag: Tag) {
        tagRepository.updateTag(tag)
    }

    suspend fun deleteTag(tag: Tag) {
        tagRepository.deleteTag(tag)
    }
}
