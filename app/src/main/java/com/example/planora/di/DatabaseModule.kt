package com.example.planora.di

import android.content.Context
import com.example.planora.data.local.PlanoraDatabase
import com.example.planora.data.local.dao.CategoryDao
import com.example.planora.data.local.dao.FocusSessionDao
import com.example.planora.data.local.dao.ReminderDao
import com.example.planora.data.local.dao.TagDao
import com.example.planora.data.local.dao.TaskDao
import com.example.planora.data.local.dao.TaskTemplateDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): PlanoraDatabase {
        return PlanoraDatabase.getDatabase(context)
    }

    @Provides
    fun provideTaskDao(db: PlanoraDatabase): TaskDao = db.taskDao()

    @Provides
    fun provideCategoryDao(db: PlanoraDatabase): CategoryDao = db.categoryDao()

    @Provides
    fun provideTagDao(db: PlanoraDatabase): TagDao = db.tagDao()

    @Provides
    fun provideReminderDao(db: PlanoraDatabase): ReminderDao = db.reminderDao()

    @Provides
    fun provideTaskTemplateDao(db: PlanoraDatabase): TaskTemplateDao = db.taskTemplateDao()

    @Provides
    fun provideFocusSessionDao(db: PlanoraDatabase): FocusSessionDao = db.focusSessionDao()
}
