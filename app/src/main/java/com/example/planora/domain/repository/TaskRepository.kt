package com.example.planora.domain.repository

import com.example.planora.domain.model.Task
import com.example.planora.domain.model.TaskFilter
import com.example.planora.domain.model.TaskSortOption
import com.example.planora.domain.model.TaskViewType
import kotlinx.coroutines.flow.Flow

interface TaskRepository {
    fun getAllActiveTasks(): Flow<List<Task>>
    fun getTaskById(taskId: String): Flow<Task?>
    fun getSmartListTasks(viewType: TaskViewType): Flow<List<Task>>
    fun searchTasks(query: String, filter: TaskFilter?, sortOption: TaskSortOption): Flow<List<Task>>

    suspend fun insertTask(task: Task)
    suspend fun updateTask(task: Task)
    suspend fun toggleTaskPin(task: Task)
    suspend fun deleteTask(task: Task)
    suspend fun completeTask(task: Task, isCompleted: Boolean)
    suspend fun moveTasksToTrash(taskIds: List<String>)
    suspend fun restoreTasksFromTrash(taskIds: List<String>)
    suspend fun emptyTrash()

    fun getActiveTaskCount(): Flow<Int>
    fun getCompletedTaskCount(): Flow<Int>
    fun getOverdueTaskCount(): Flow<Int>
    fun getTodayTaskCount(): Flow<Int>
}
