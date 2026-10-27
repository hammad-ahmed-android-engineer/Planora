package com.example.planora.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.planora.data.local.dao.CategoryDao
import com.example.planora.data.local.dao.FocusSessionDao
import com.example.planora.data.local.dao.ReminderDao
import com.example.planora.data.local.dao.TagDao
import com.example.planora.data.local.dao.TaskDao
import com.example.planora.data.local.dao.TaskTemplateDao
import com.example.planora.data.local.entity.CategoryEntity
import com.example.planora.data.local.entity.FocusSessionEntity
import com.example.planora.data.local.entity.ReminderEntity
import com.example.planora.data.local.entity.SubtaskEntity
import com.example.planora.data.local.entity.TagEntity
import com.example.planora.data.local.entity.TaskEntity
import com.example.planora.data.local.entity.TaskTagCrossRef
import com.example.planora.data.local.entity.TaskTemplateEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
    entities = [
        TaskEntity::class,
        SubtaskEntity::class,
        CategoryEntity::class,
        TagEntity::class,
        TaskTagCrossRef::class,
        ReminderEntity::class,
        TaskTemplateEntity::class,
        FocusSessionEntity::class,
    ],
    version = 2,
    exportSchema = true
)
abstract class PlanoraDatabase : RoomDatabase() {

    abstract fun taskDao(): TaskDao
    abstract fun categoryDao(): CategoryDao
    abstract fun tagDao(): TagDao
    abstract fun reminderDao(): ReminderDao
    abstract fun taskTemplateDao(): TaskTemplateDao
    abstract fun focusSessionDao(): FocusSessionDao

    companion object {
        @Volatile
        private var INSTANCE: PlanoraDatabase? = null

        // Placeholder for future database migrations to preserve user data on version bumps
        private val ALL_MIGRATIONS: Array<Migration> = arrayOf()

        fun getDatabase(context: Context): PlanoraDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    PlanoraDatabase::class.java,
                    "planora_database"
                )
                    .addCallback(PlanoraDatabaseCallback())
                    .addMigrations(*ALL_MIGRATIONS)
                    .build()
                INSTANCE = instance
                instance
            }
        }

        private class PlanoraDatabaseCallback : Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                INSTANCE?.let { database ->
                    CoroutineScope(Dispatchers.IO).launch {
                        populateDefaultData(database)
                    }
                }
            }

            override fun onOpen(db: SupportSQLiteDatabase) {
                super.onOpen(db)
                INSTANCE?.let { database ->
                    CoroutineScope(Dispatchers.IO).launch {
                        ensureDefaultCategoriesExist(database)
                    }
                }
            }

            private suspend fun populateDefaultData(database: PlanoraDatabase) {
                ensureDefaultCategoriesExist(database)

                // Populate Default Templates
                val defaultTemplates = listOf(
                    TaskTemplateEntity(
                        id = "tpl_study",
                        title = "Study Session",
                        description = "Review material and practice problems",
                        priority = "MEDIUM",
                        categoryId = "cat_study",
                        relativeDueDays = 0,
                        defaultSubtasksJson = "[\"Read assigned chapter\",\"Summarize key points\",\"Solve practice problems\"]"
                    ),
                    TaskTemplateEntity(
                        id = "tpl_shopping",
                        title = "Grocery Shopping",
                        description = "Weekly grocery store trip",
                        priority = "LOW",
                        categoryId = "cat_shopping",
                        relativeDueDays = 1,
                        defaultSubtasksJson = "[\"Fresh produce\",\"Dairy & eggs\",\"Pantry essentials\"]"
                    ),
                    TaskTemplateEntity(
                        id = "tpl_weekly_review",
                        title = "Weekly Productivity Review",
                        description = "Reflect on completed work and plan upcoming tasks",
                        priority = "HIGH",
                        categoryId = "cat_personal",
                        relativeDueDays = 7,
                        defaultSubtasksJson = "[\"Review completed tasks\",\"Clear inbox\",\"Set priorities for next week\"]"
                    )
                )
                database.taskTemplateDao().insertTemplates(defaultTemplates)
            }

            private suspend fun ensureDefaultCategoriesExist(database: PlanoraDatabase) {
                val existingCategories = database.categoryDao().getAllCategoriesDirect()
                if (existingCategories.isEmpty()) {
                    val defaultCategories = listOf(
                        CategoryEntity(id = "cat_personal", name = "Personal", colorHex = "#4285F4", iconName = "person"),
                        CategoryEntity(id = "cat_work", name = "Work", colorHex = "#EA4335", iconName = "work"),
                        CategoryEntity(id = "cat_study", name = "Study", colorHex = "#FBBC05", iconName = "school"),
                        CategoryEntity(id = "cat_shopping", name = "Shopping", colorHex = "#34A853", iconName = "shopping_cart"),
                        CategoryEntity(id = "cat_health", name = "Health", colorHex = "#FF6D00", iconName = "fitness_center"),
                        CategoryEntity(id = "cat_other", name = "Other", colorHex = "#A142F4", iconName = "more_horiz")
                    )
                    database.categoryDao().insertCategories(defaultCategories)
                }
            }
        }
    }
}
