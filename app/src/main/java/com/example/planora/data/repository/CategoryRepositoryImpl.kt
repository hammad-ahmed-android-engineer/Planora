package com.example.planora.data.repository

import com.example.planora.data.local.dao.CategoryDao
import com.example.planora.data.local.entity.toDomain
import com.example.planora.data.local.entity.toEntity
import com.example.planora.domain.model.Category
import com.example.planora.domain.repository.CategoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

import javax.inject.Inject

class CategoryRepositoryImpl @Inject constructor(
    private val categoryDao: CategoryDao
) : CategoryRepository {

    override fun getAllCategories(): Flow<List<Category>> {
        return categoryDao.getAllCategories().map { list -> list.map { it.toDomain() } }
    }

    override suspend fun getCategoryById(id: String): Category? {
        return categoryDao.getCategoryById(id)?.toDomain()
    }

    override suspend fun insertCategory(category: Category) {
        categoryDao.insertCategory(category.toEntity())
    }

    override suspend fun updateCategory(category: Category) {
        categoryDao.updateCategory(category.toEntity())
    }

    override suspend fun deleteCategory(category: Category, reassignToCategoryId: String?) {
        categoryDao.reassignTaskCategories(category.id, reassignToCategoryId)
        categoryDao.deleteCategory(category.toEntity())
    }
}
