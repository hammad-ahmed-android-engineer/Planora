package com.example.planora.data.repository

import android.content.Context
import android.net.Uri
import com.example.planora.domain.model.PlanoraBackupData
import com.example.planora.domain.model.TaskViewType
import com.example.planora.domain.repository.BackupRestoreRepository
import com.example.planora.domain.repository.CategoryRepository
import com.example.planora.domain.repository.TagRepository
import com.example.planora.domain.repository.TaskRepository
import com.example.planora.domain.repository.TaskTemplateRepository
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import kotlinx.coroutines.flow.first
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BackupRestoreRepositoryImpl @Inject constructor(
    private val taskRepository: TaskRepository,
    private val categoryRepository: CategoryRepository,
    private val tagRepository: TagRepository,
    private val templateRepository: TaskTemplateRepository
) : BackupRestoreRepository {

    private val gson: Gson = GsonBuilder().setPrettyPrinting().create()

    override suspend fun createJsonBackup(): String {
        val tasks = taskRepository.getAllActiveTasks().first()
        val completed = taskRepository.getSmartListTasks(TaskViewType.COMPLETED).first()
        val categories = categoryRepository.getAllCategories().first()
        val tags = tagRepository.getAllTags().first()
        val templates = templateRepository.getAllTemplates().first()

        val backup = PlanoraBackupData(
            tasks = tasks + completed,
            categories = categories,
            tags = tags,
            templates = templates
        )
        return gson.toJson(backup)
    }

    override suspend fun exportToCsv(): String {
        val tasks = taskRepository.getAllActiveTasks().first()
        val sb = StringBuilder()
        sb.append("ID,Title,Description,DueDate,Priority,Completed,Category\n")
        tasks.forEach { t ->
            val title = t.title.replace("\"", "\"\"")
            val desc = t.description.replace("\"", "\"\"")
            val cat = t.category?.name ?: ""
            sb.append("\"${t.id}\",\"$title\",\"$desc\",\"${t.dueDate ?: ""}\",\"${t.priority.name}\",${t.isCompleted},\"$cat\"\n")
        }
        return sb.toString()
    }

    override suspend fun exportData(context: Context, uri: Uri): Result<Unit> {
        return try {
            val json = createJsonBackup()
            context.contentResolver.openOutputStream(uri)?.use { outputStream ->
                outputStream.write(json.toByteArray(Charsets.UTF_8))
            } ?: return Result.failure(IllegalStateException("Failed to open output stream"))
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun importData(context: Context, uri: Uri): Result<Int> {
        return try {
            val jsonString = context.contentResolver.openInputStream(uri)?.use { inputStream ->
                inputStream.bufferedReader(Charsets.UTF_8).use { it.readText() }
            } ?: return Result.failure(IllegalStateException("Failed to open input stream"))
            restoreFromJsonBackup(jsonString)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun restoreFromJsonBackup(jsonString: String): Result<Int> {
        return try {
            val backupData = gson.fromJson(jsonString, PlanoraBackupData::class.java)
                ?: return Result.failure(IllegalArgumentException("Invalid or empty backup file"))

            var restoredCount = 0

            backupData.categories.forEach { cat ->
                categoryRepository.insertCategory(cat)
            }

            backupData.tags.forEach { tag ->
                tagRepository.insertTag(tag)
            }

            backupData.templates.forEach { tpl ->
                templateRepository.insertTemplate(tpl)
            }

            backupData.tasks.forEach { task ->
                taskRepository.insertTask(task)
                restoredCount++
            }

            Result.success(restoredCount)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
