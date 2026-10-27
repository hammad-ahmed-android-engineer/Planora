package com.example.planora.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.planora.data.local.entity.ReminderEntity
import com.example.planora.data.local.entity.SubtaskEntity
import com.example.planora.data.local.entity.TaskEntity
import com.example.planora.data.local.entity.TaskTagCrossRef
import com.example.planora.data.local.entity.TaskWithDetails
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {

    @Transaction
    @Query("SELECT * FROM tasks WHERE isDeleted = 0 AND isArchived = 0 ORDER BY isPinned DESC, priority DESC, createdTimestamp DESC")
    fun getAllActiveTasksWithDetails(): Flow<List<TaskWithDetails>>

    @Transaction
    @Query("SELECT * FROM tasks WHERE isDeleted = 0 AND isArchived = 0 AND isCompleted = 0 ORDER BY isPinned DESC, priority DESC, createdTimestamp DESC")
    fun getIncompleteTasks(): Flow<List<TaskWithDetails>>

    @Transaction
    @Query("SELECT * FROM tasks WHERE id = :taskId")
    fun getTaskWithDetailsById(taskId: String): Flow<TaskWithDetails?>

    @Transaction
    @Query("SELECT * FROM tasks WHERE id = :taskId")
    suspend fun getTaskWithDetailsByIdDirect(taskId: String): TaskWithDetails?

    @Transaction
    @Query("SELECT * FROM tasks WHERE isDeleted = 0 AND isArchived = 0 AND categoryId IS NULL ORDER BY isPinned DESC, createdTimestamp DESC")
    fun getInboxTasks(): Flow<List<TaskWithDetails>>

    @Transaction
    @Query("SELECT * FROM tasks WHERE isDeleted = 0 AND isArchived = 0 AND dueDate = :todayDate ORDER BY isPinned DESC, priority DESC")
    fun getTodayTasks(todayDate: String): Flow<List<TaskWithDetails>>

    @Transaction
    @Query("SELECT * FROM tasks WHERE isDeleted = 0 AND isArchived = 0 AND dueDate > :todayDate ORDER BY dueDate ASC, priority DESC")
    fun getUpcomingTasks(todayDate: String): Flow<List<TaskWithDetails>>

    @Transaction
    @Query("SELECT * FROM tasks WHERE isDeleted = 0 AND isArchived = 0 AND priority IN ('HIGH', 'URGENT') ORDER BY priority DESC, dueDate ASC")
    fun getHighPriorityTasks(): Flow<List<TaskWithDetails>>

    @Transaction
    @Query("SELECT * FROM tasks WHERE isDeleted = 0 AND isArchived = 0 AND isCompleted = 1 ORDER BY completionTimestamp DESC")
    fun getCompletedTasks(): Flow<List<TaskWithDetails>>

    @Transaction
    @Query("SELECT * FROM tasks WHERE isDeleted = 0 AND isArchived = 0 AND isCompleted = 1 ORDER BY completionTimestamp DESC")
    suspend fun getCompletedTasksDirect(): List<TaskWithDetails>

    @Transaction
    @Query("SELECT * FROM tasks WHERE isDeleted = 0 AND isArchived = 0 AND isCompleted = 0 AND dueDate < :todayDate ORDER BY dueDate ASC, priority DESC")
    fun getOverdueTasks(todayDate: String): Flow<List<TaskWithDetails>>

    @Transaction
    @Query("SELECT * FROM tasks WHERE isDeleted = 0 AND isArchived = 1 ORDER BY lastModifiedTimestamp DESC")
    fun getArchivedTasks(): Flow<List<TaskWithDetails>>

    @Transaction
    @Query("SELECT * FROM tasks WHERE isDeleted = 1 ORDER BY lastModifiedTimestamp DESC")
    fun getTrashTasks(): Flow<List<TaskWithDetails>>

    @Transaction
    @Query("SELECT * FROM tasks WHERE isDeleted = 0 AND isArchived = 0 AND isStarred = 1 ORDER BY priority DESC, createdTimestamp DESC")
    fun getFavoriteTasks(): Flow<List<TaskWithDetails>>

    @Transaction
    @Query("SELECT * FROM tasks WHERE isDeleted = 0 AND isArchived = 0 AND isPinned = 1 ORDER BY priority DESC, createdTimestamp DESC")
    fun getPinnedTasks(): Flow<List<TaskWithDetails>>

    @Transaction
    @Query("SELECT * FROM tasks WHERE isDeleted = 0 AND isArchived = 0 AND categoryId = :categoryId ORDER BY isCompleted ASC, priority DESC, dueDate ASC")
    fun getTasksByCategory(categoryId: String): Flow<List<TaskWithDetails>>

    @Transaction
    @Query("""
        SELECT DISTINCT t.* FROM tasks t
        LEFT JOIN subtasks s ON t.id = s.taskId
        WHERE t.isDeleted = 0 AND t.isArchived = 0
        AND (
            :searchQuery = '' OR
            LOWER(t.title) LIKE '%' || LOWER(:searchQuery) || '%' OR
            LOWER(t.description) LIKE '%' || LOWER(:searchQuery) || '%' OR
            LOWER(t.notes) LIKE '%' || LOWER(:searchQuery) || '%' OR
            LOWER(s.title) LIKE '%' || LOWER(:searchQuery) || '%'
        )
        ORDER BY t.isPinned DESC, t.priority DESC, t.createdTimestamp DESC
    """)
    fun searchActiveTasksByQuery(searchQuery: String): Flow<List<TaskWithDetails>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(task: TaskEntity)

    @Update
    suspend fun updateTask(task: TaskEntity)

    @Delete
    suspend fun deleteTask(task: TaskEntity)

    @Query("DELETE FROM tasks WHERE isDeleted = 1")
    suspend fun emptyTrash()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSubtasks(subtasks: List<SubtaskEntity>)

    @Query("DELETE FROM subtasks WHERE taskId = :taskId")
    suspend fun deleteSubtasksForTask(taskId: String)

    @Update
    suspend fun updateSubtask(subtask: SubtaskEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReminders(reminders: List<ReminderEntity>)

    @Query("DELETE FROM reminders WHERE taskId = :taskId")
    suspend fun deleteRemindersForTask(taskId: String)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTaskTagCrossRefs(crossRefs: List<TaskTagCrossRef>)

    @Query("DELETE FROM task_tag_cross_ref WHERE taskId = :taskId")
    suspend fun deleteTaskTagCrossRefsForTask(taskId: String)

    @Query("UPDATE tasks SET isDeleted = 1, lastModifiedTimestamp = :timestamp WHERE id IN (:taskIds)")
    suspend fun moveTasksToTrash(taskIds: List<String>, timestamp: Long = System.currentTimeMillis())

    @Query("UPDATE tasks SET isDeleted = 0, lastModifiedTimestamp = :timestamp WHERE id IN (:taskIds)")
    suspend fun restoreTasksFromTrash(taskIds: List<String>, timestamp: Long = System.currentTimeMillis())

    @Query("UPDATE tasks SET isCompleted = :isCompleted, completionTimestamp = :completionTimestamp, lastModifiedTimestamp = :timestamp WHERE id = :taskId")
    suspend fun updateTaskCompletion(taskId: String, isCompleted: Boolean, completionTimestamp: Long?, timestamp: Long = System.currentTimeMillis())

    @Query("SELECT COUNT(*) FROM tasks WHERE isDeleted = 0 AND isArchived = 0 AND isCompleted = 0")
    fun getActiveTaskCount(): Flow<Int>

    @Query("SELECT COUNT(*) FROM tasks WHERE isDeleted = 0 AND isArchived = 0 AND isCompleted = 1")
    fun getCompletedTaskCount(): Flow<Int>

    @Query("SELECT COUNT(*) FROM tasks WHERE isDeleted = 0 AND isArchived = 0 AND isCompleted = 0 AND dueDate < :todayDate")
    fun getOverdueTaskCount(todayDate: String): Flow<Int>

    @Query("SELECT COUNT(*) FROM tasks WHERE isDeleted = 0 AND isArchived = 0 AND dueDate = :todayDate AND isCompleted = 0")
    fun getTodayTaskCount(todayDate: String): Flow<Int>
}
