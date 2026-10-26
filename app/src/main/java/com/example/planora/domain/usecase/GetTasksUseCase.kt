package com.example.planora.domain.usecase

import com.example.planora.domain.model.Task
import com.example.planora.domain.model.TaskFilter
import com.example.planora.domain.model.TaskSortOption
import com.example.planora.domain.model.TaskViewType
import com.example.planora.domain.repository.TaskRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * UseCase for querying tasks by view types, filters, or search criteria.
 */
class GetTasksUseCase @Inject constructor(
    private val taskRepository: TaskRepository
) {
    /**
     * Returns a Flow of all active non-deleted, non-archived tasks.
     */
    fun getAllActiveTasks(): Flow<List<Task>> {
        return taskRepository.getAllActiveTasks()
    }

    /**
     * Retrieves task details by task ID.
     */
    fun getTaskById(taskId: String): Flow<Task?> {
        return taskRepository.getTaskById(taskId)
    }

    /**
     * Returns tasks for specific smart list view type (e.g. TODAY, UPCOMING, STARRED, TRASH).
     */
    fun getSmartListTasks(viewType: TaskViewType): Flow<List<Task>> {
        return taskRepository.getSmartListTasks(viewType)
    }

    fun getActiveTaskCount(): Flow<Int> {
        return taskRepository.getActiveTaskCount()
    }

    fun getCompletedTaskCount(): Flow<Int> {
        return taskRepository.getCompletedTaskCount()
    }

    /**
     * Searches tasks with given query, filter criteria, and sort options.
     */
    fun searchTasks(
        query: String,
        filter: TaskFilter?,
        sortOption: TaskSortOption
    ): Flow<List<Task>> {
        return taskRepository.searchTasks(query, filter, sortOption)
    }
}
