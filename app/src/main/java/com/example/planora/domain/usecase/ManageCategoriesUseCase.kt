package com.example.planora.domain.usecase

import com.example.planora.domain.model.Category
import com.example.planora.domain.repository.CategoryRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ManageCategoriesUseCase @Inject constructor(
    private val categoryRepository: CategoryRepository
) {
    fun getAllCategories(): Flow<List<Category>> {
        return categoryRepository.getAllCategories()
    }

    suspend fun addCategory(category: Category) {
        categoryRepository.insertCategory(category)
    }

    suspend fun updateCategory(category: Category) {
        categoryRepository.updateCategory(category)
    }

    suspend fun deleteCategory(category: Category, reassignToId: String?) {
        categoryRepository.deleteCategory(category, reassignToId)
    }
}
