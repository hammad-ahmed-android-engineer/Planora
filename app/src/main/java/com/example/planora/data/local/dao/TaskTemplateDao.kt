package com.example.planora.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.planora.data.local.entity.TaskTemplateEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskTemplateDao {

    @Query("SELECT * FROM task_templates ORDER BY title ASC")
    fun getAllTemplates(): Flow<List<TaskTemplateEntity>>

    @Query("SELECT * FROM task_templates")
    suspend fun getAllTemplatesDirect(): List<TaskTemplateEntity>

    @Query("SELECT * FROM task_templates WHERE id = :id")
    suspend fun getTemplateById(id: String): TaskTemplateEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTemplate(template: TaskTemplateEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTemplates(templates: List<TaskTemplateEntity>)

    @Update
    suspend fun updateTemplate(template: TaskTemplateEntity)

    @Delete
    suspend fun deleteTemplate(template: TaskTemplateEntity)
}
