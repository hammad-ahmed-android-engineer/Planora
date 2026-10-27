package com.example.planora.data.repository

import com.example.planora.data.local.dao.TagDao
import com.example.planora.data.local.entity.toDomain
import com.example.planora.data.local.entity.toEntity
import com.example.planora.domain.model.Tag
import com.example.planora.domain.repository.TagRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

import javax.inject.Inject

class TagRepositoryImpl @Inject constructor(
    private val tagDao: TagDao
) : TagRepository {

    override fun getAllTags(): Flow<List<Tag>> {
        return tagDao.getAllTags().map { list -> list.map { it.toDomain() } }
    }

    override suspend fun insertTag(tag: Tag) {
        tagDao.insertTag(tag.toEntity())
    }

    override suspend fun updateTag(tag: Tag) {
        tagDao.updateTag(tag.toEntity())
    }

    override suspend fun deleteTag(tag: Tag) {
        tagDao.deleteTag(tag.toEntity())
    }
}
