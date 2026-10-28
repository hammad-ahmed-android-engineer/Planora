package com.example.planora.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.planora.domain.model.Priority
import com.example.planora.domain.model.Task
import com.example.planora.domain.model.TaskFilter
import com.example.planora.domain.model.TaskSortOption
import com.example.planora.domain.usecase.DeleteTaskUseCase
import com.example.planora.domain.usecase.GetTasksUseCase
import com.example.planora.domain.usecase.SaveTaskUseCase
import com.example.planora.domain.usecase.ToggleTaskCompletionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

data class SearchFilterUiState(
    val query: String = "",
    val selectedPriority: Priority? = null,
    val selectedCategoryId: String? = null,
    val sortOption: TaskSortOption = TaskSortOption.DUE_DATE,
    val searchResults: List<Task> = emptyList()
)

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class SearchFilterViewModel @Inject constructor(
    val getTasksUseCase: GetTasksUseCase,
    private val toggleTaskCompletionUseCase: ToggleTaskCompletionUseCase,
    private val saveTaskUseCase: SaveTaskUseCase,
    private val deleteTaskUseCase: DeleteTaskUseCase
) : ViewModel() {

    private val _query = MutableStateFlow("")
    private val _selectedPriority = MutableStateFlow<Priority?>(null)
    private val _selectedCategoryId = MutableStateFlow<String?>(null)
    private val _sortOption = MutableStateFlow(TaskSortOption.DUE_DATE)

    private val searchResultsFlow: Flow<List<Task>> = combine(
        _query,
        _selectedPriority,
        _selectedCategoryId,
        _sortOption
    ) { query, priority, categoryId, sort ->
        Triple(query, TaskFilter(priority = priority, categoryId = categoryId), sort)
    }.flatMapLatest { (q, filter, sort) ->
        getTasksUseCase.searchTasks(q, filter, sort)
    }

    val uiState: StateFlow<SearchFilterUiState> = combine(
        _query,
        _selectedPriority,
        _selectedCategoryId,
        _sortOption,
        searchResultsFlow
    ) { query, priority, categoryId, sort, results ->
        SearchFilterUiState(
            query = query,
            selectedPriority = priority,
            selectedCategoryId = categoryId,
            sortOption = sort,
            searchResults = results
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = SearchFilterUiState()
    )

    fun updateQuery(q: String) {
        _query.value = q
    }

    fun setPriority(prio: Priority?) {
        _selectedPriority.value = prio
    }

    fun setCategoryId(catId: String?) {
        _selectedCategoryId.value = catId
    }

    fun setSortOption(sort: TaskSortOption) {
        _sortOption.value = sort
    }

    fun clearFilters() {
        _query.value = ""
        _selectedPriority.value = null
        _selectedCategoryId.value = null
        _sortOption.value = TaskSortOption.DUE_DATE
    }

    fun toggleTaskComplete(task: Task) {
        viewModelScope.launch {
            toggleTaskCompletionUseCase(task, !task.isCompleted)
        }
    }

    fun toggleTaskFavorite(task: Task) {
        viewModelScope.launch {
            saveTaskUseCase(task.copy(isStarred = !task.isStarred))
        }
    }

    fun toggleTaskPin(task: Task) {
        viewModelScope.launch {
            saveTaskUseCase.toggleTaskPin(task)
        }
    }

    fun deleteTask(task: Task) {
        viewModelScope.launch {
            deleteTaskUseCase(task)
        }
    }
}
