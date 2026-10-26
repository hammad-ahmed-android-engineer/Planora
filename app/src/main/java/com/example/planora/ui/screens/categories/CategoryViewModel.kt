package com.example.planora.ui.screens.categories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.planora.domain.model.Category
import com.example.planora.domain.usecase.ManageCategoriesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

data class CategoryUiState(
    val categories: List<Category> = emptyList(),
    val isLoading: Boolean = false
)

@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val manageCategoriesUseCase: ManageCategoriesUseCase
) : ViewModel() {

    val uiState: StateFlow<CategoryUiState> = manageCategoriesUseCase.getAllCategories()
        .map { CategoryUiState(categories = it, isLoading = false) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = CategoryUiState(isLoading = true)
        )

    fun addCategory(name: String, colorHex: String = "#4285F4") {
        if (name.isBlank()) return
        viewModelScope.launch {
            manageCategoriesUseCase.addCategory(Category(name = name.trim(), colorHex = colorHex))
        }
    }

    fun updateCategory(category: Category) {
        viewModelScope.launch {
            manageCategoriesUseCase.updateCategory(category)
        }
    }

    fun deleteCategory(category: Category, reassignToId: String?) {
        viewModelScope.launch {
            manageCategoriesUseCase.deleteCategory(category, reassignToId)
        }
    }
}
