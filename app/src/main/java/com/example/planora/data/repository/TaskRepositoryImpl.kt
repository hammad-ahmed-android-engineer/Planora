package com.example.planora.data.repository

import com.example.planora.core.utils.calculateNextDueDate
import com.example.planora.data.local.dao.TaskDao
import com.example.planora.data.local.entity.TaskTagCrossRef
import com.example.planora.data.mapper.toDomain
import com.example.planora.data.mapper.toEntity
import com.example.planora.domain.model.Priority
import com.example.planora.domain.model.RecurrenceType
import com.example.planora.domain.model.Task
import com.example.planora.domain.model.TaskFilter
import com.example.planora.domain.model.TaskSortOption
import com.example.planora.domain.model.TaskViewType
import com.example.planora.domain.repository.TaskRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDate
import java.util.UUID
import javax.inject.Inject

class TaskRepositoryImpl @Inject constructor(
    private val taskDao: TaskDao
) : TaskRepository {

    override fun getAllActiveTasks(): Flow<List<Task>> {
        return taskDao.getAllActiveTasksWithDetails().map { list ->
            list.map { it.toDomain() }
        }
    }

    override fun getTaskById(taskId: String): Flow<Task?> {
        return taskDao.getTaskWithDetailsById(taskId).map { it?.toDomain() }
    }

    override fun getSmartListTasks(viewType: TaskViewType): Flow<List<Task>> {
        val todayStr = LocalDate.now().toString()
        val flow = when (viewType) {
            TaskViewType.INBOX -> taskDao.getInboxTasks()
            TaskViewType.TODAY -> taskDao.getTodayTasks(todayStr)
            TaskViewType.UPCOMING -> taskDao.getUpcomingTasks(todayStr)
            TaskViewType.NEXT_7_DAYS -> taskDao.getUpcomingTasks(todayStr)
            TaskViewType.ALL_TASKS -> taskDao.getAllActiveTasksWithDetails()
            TaskViewType.IN_PROGRESS -> taskDao.getIncompleteTasks()
            TaskViewType.COMPLETED -> taskDao.getCompletedTasks()
            TaskViewType.OVERDUE -> taskDao.getOverdueTasks(todayStr)
            TaskViewType.NO_DUE_DATE -> taskDao.getInboxTasks()
            TaskViewType.HIGH_PRIORITY -> taskDao.getHighPriorityTasks()
            TaskViewType.FAVORITES -> taskDao.getFavoriteTasks()
            TaskViewType.PINNED -> taskDao.getPinnedTasks()
            TaskViewType.RECENTLY_ADDED -> taskDao.getAllActiveTasksWithDetails()
            TaskViewType.RECENTLY_COMPLETED -> taskDao.getCompletedTasks()
            TaskViewType.ARCHIVED -> taskDao.getArchivedTasks()
            TaskViewType.TRASH -> taskDao.getTrashTasks()
        }
        return flow.map { list -> list.map { it.toDomain() } }
    }

    override fun searchTasks(
        query: String,
        filter: TaskFilter?,
        sortOption: TaskSortOption
    ): Flow<List<Task>> {
        val trimmedQuery = query.trim()
        val flow = if (trimmedQuery.isNotBlank()) {
            taskDao.searchActiveTasksByQuery(trimmedQuery)
        } else {
            taskDao.getAllActiveTasksWithDetails()
        }

        return flow.map { list ->
            var domainList = list.map { it.toDomain() }.filter { !it.isDeleted && !it.isArchived }

            if (trimmedQuery.isNotBlank()) {
                val q = trimmedQuery.lowercase()
                domainList = domainList.filter {
                    it.title.lowercase().contains(q) ||
                    it.description.lowercase().contains(q) ||
                    it.notes.lowercase().contains(q) ||
                    it.category?.name?.lowercase()?.contains(q) == true ||
                    it.tags.any { tag -> tag.name.lowercase().contains(q) } ||
                    it.subtasks.any { sub -> sub.title.lowercase().contains(q) }
                }
            }

            // Apply Filters
            filter?.let { f ->
                f.categoryId?.let { catId -> domainList = domainList.filter { it.category?.id == catId } }
                f.priority?.let { p -> domainList = domainList.filter { it.priority == p } }
                f.isCompleted?.let { comp -> domainList = domainList.filter { it.isCompleted == comp } }
                f.isStarred?.let { star -> domainList = domainList.filter { it.isStarred == star } }
                f.isPinned?.let { pin -> domainList = domainList.filter { it.isPinned == pin } }
                f.isOverdue?.let { ovd -> domainList = domainList.filter { it.isOverdue == ovd } }
                if (f.tagIds.isNotEmpty()) {
                    domainList = domainList.filter { task -> task.tags.any { tag -> tag.id in f.tagIds } }
                }
                f.hasSubtasks?.let { hasSub -> domainList = domainList.filter { (it.subtasks.isNotEmpty()) == hasSub } }
                f.hasReminders?.let { hasRem -> domainList = domainList.filter { (it.reminders.isNotEmpty()) == hasRem } }
            }

            // Apply Sorting
            domainList = when (sortOption) {
                TaskSortOption.DUE_DATE -> domainList.sortedBy { it.dueDate ?: "9999-12-31" }
                TaskSortOption.PRIORITY -> domainList.sortedByDescending { it.priority.level }
                TaskSortOption.TITLE -> domainList.sortedBy { it.title.lowercase() }
                TaskSortOption.CREATION_DATE -> domainList.sortedByDescending { it.createdTimestamp }
                TaskSortOption.LAST_MODIFIED -> domainList.sortedByDescending { it.lastModifiedTimestamp }
                TaskSortOption.ESTIMATED_DURATION -> domainList.sortedByDescending { it.estimatedDurationMinutes }
            }

            domainList
        }
    }

    override suspend fun insertTask(task: Task) {
        taskDao.insertTask(task.toEntity())
        saveTaskRelations(task)
    }

    override suspend fun updateTask(task: Task) {
        taskDao.updateTask(task.toEntity())
        saveTaskRelations(task)
    }

    private suspend fun saveTaskRelations(task: Task) {
        // Save subtasks
        taskDao.deleteSubtasksForTask(task.id)
        if (task.subtasks.isNotEmpty()) {
            taskDao.insertSubtasks(task.subtasks.map { it.toEntity(task.id) })
        }

        // Save reminders
        taskDao.deleteRemindersForTask(task.id)
        if (task.reminders.isNotEmpty()) {
            taskDao.insertReminders(task.reminders.map { it.toEntity(task.id) })
        }

        // Save tags cross ref
        taskDao.deleteTaskTagCrossRefsForTask(task.id)
        if (task.tags.isNotEmpty()) {
            taskDao.insertTaskTagCrossRefs(task.tags.map { TaskTagCrossRef(task.id, it.id) })
        }
    }

    override suspend fun deleteTask(task: Task) {
        taskDao.deleteSubtasksForTask(task.id)
        taskDao.deleteRemindersForTask(task.id)
        taskDao.deleteTaskTagCrossRefsForTask(task.id)
        taskDao.deleteTask(task.toEntity())
    }

    override suspend fun toggleTaskPin(task: Task) {
        updateTask(task.copy(isPinned = !task.isPinned, lastModifiedTimestamp = System.currentTimeMillis()))
    }

    override suspend fun completeTask(task: Task, isCompleted: Boolean) {
        val now = System.currentTimeMillis()
        val updatedTask = task.copy(
            isCompleted = isCompleted,
            completionTimestamp = if (isCompleted) now else null,
            lastModifiedTimestamp = now
        )
        taskDao.updateTask(updatedTask.toEntity())

        // Handle Recurrence if task is completed
        if (isCompleted && task.recurrenceType != RecurrenceType.NONE) {
            val nextDueDate = task.calculateNextDueDate()
            if (nextDueDate != null) {
                val nextOccurrence = task.copy(
                    id = UUID.randomUUID().toString(),
                    isCompleted = false,
                    completionTimestamp = null,
                    createdTimestamp = now,
                    lastModifiedTimestamp = now,
                    dueDate = nextDueDate,
                    subtasks = task.subtasks.map { it.copy(id = UUID.randomUUID().toString(), isCompleted = false) }
                )
                insertTask(nextOccurrence)
            }
        }
    }

    override suspend fun moveTasksToTrash(taskIds: List<String>) {
        taskDao.moveTasksToTrash(taskIds)
    }

    override suspend fun restoreTasksFromTrash(taskIds: List<String>) {
        taskDao.restoreTasksFromTrash(taskIds)
    }

    override suspend fun emptyTrash() {
        taskDao.emptyTrash()
    }

    override fun getActiveTaskCount(): Flow<Int> = taskDao.getActiveTaskCount()
    override fun getCompletedTaskCount(): Flow<Int> = taskDao.getCompletedTaskCount()
    override fun getOverdueTaskCount(): Flow<Int> = taskDao.getOverdueTaskCount(LocalDate.now().toString())
    override fun getTodayTaskCount(): Flow<Int> = taskDao.getTodayTaskCount(LocalDate.now().toString())
}
