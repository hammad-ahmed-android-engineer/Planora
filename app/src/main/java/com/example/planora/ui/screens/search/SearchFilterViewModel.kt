package com.example.planora.ui.screens.search

import androidx.lifecycle.SavedStateHandle
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
    private val getTasksUseCase: GetTasksUseCase,
    private val toggleTaskCompletionUseCase: ToggleTaskCompletionUseCase,
    private val saveTaskUseCase: SaveTaskUseCase,
    private val deleteTaskUseCase: DeleteTaskUseCase,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    companion object {
        private const val KEY_SEARCH_QUERY = "search_query"
        private const val KEY_SEARCH_PRIORITY = "search_priority"
        private const val KEY_SEARCH_CATEGORY = "search_category"
        private const val KEY_SEARCH_SORT = "search_sort"
    }

    private val queryFlow = savedStateHandle.getStateFlow(KEY_SEARCH_QUERY, "")
    private val priorityFlow = savedStateHandle.getStateFlow<String?>(KEY_SEARCH_PRIORITY, null)
    private val categoryFlow = savedStateHandle.getStateFlow<String?>(KEY_SEARCH_CATEGORY, null)
    private val sortFlow = savedStateHandle.getStateFlow(KEY_SEARCH_SORT, TaskSortOption.DUE_DATE.name)

    val uiState: StateFlow<SearchFilterUiState> = combine(
        queryFlow,
        priorityFlow,
        categoryFlow,
        sortFlow
    ) { query, priorityStr, catId, sortStr ->
        val priority = priorityStr?.let { runCatching { Priority.valueOf(it) }.getOrNull() }
        val sort = runCatching { TaskSortOption.valueOf(sortStr) }.getOrDefault(TaskSortOption.DUE_DATE)
        val filter = TaskFilter(priority = priority, categoryId = catId)
        Triple(query, filter, sort)
    }.flatMapLatest { (q, filter, sort) ->
        getTasksUseCase.searchTasks(q, filter, sort).combine(queryFlow) { results, query ->
            val priority = priorityFlow.value?.let { runCatching { Priority.valueOf(it) }.getOrNull() }
            val sortOption = runCatching { TaskSortOption.valueOf(sortFlow.value) }.getOrDefault(TaskSortOption.DUE_DATE)
            SearchFilterUiState(
                query = query,
                selectedPriority = priority,
                selectedCategoryId = categoryFlow.value,
                sortOption = sortOption,
                searchResults = results
            )
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = SearchFilterUiState()
    )

    fun updateQuery(q: String) {
        savedStateHandle[KEY_SEARCH_QUERY] = q
    }

    fun setPriority(prio: Priority?) {
        savedStateHandle[KEY_SEARCH_PRIORITY] = prio?.name
    }

    fun setCategoryId(catId: String?) {
        savedStateHandle[KEY_SEARCH_CATEGORY] = catId
    }

    fun setSortOption(sort: TaskSortOption) {
        savedStateHandle[KEY_SEARCH_SORT] = sort.name
    }

    fun clearFilters() {
        savedStateHandle[KEY_SEARCH_QUERY] = ""
        savedStateHandle[KEY_SEARCH_PRIORITY] = null
        savedStateHandle[KEY_SEARCH_CATEGORY] = null
        savedStateHandle[KEY_SEARCH_SORT] = TaskSortOption.DUE_DATE.name
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
