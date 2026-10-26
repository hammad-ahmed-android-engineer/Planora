package com.example.planora.di

import com.example.planora.data.repository.BackupRestoreRepositoryImpl
import com.example.planora.data.repository.CategoryRepositoryImpl
import com.example.planora.data.repository.FocusRepositoryImpl
import com.example.planora.data.repository.PreferencesRepositoryImpl
import com.example.planora.data.repository.TagRepositoryImpl
import com.example.planora.data.repository.TaskRepositoryImpl
import com.example.planora.data.repository.TaskTemplateRepositoryImpl
import com.example.planora.domain.repository.BackupRestoreRepository
import com.example.planora.domain.repository.CategoryRepository
import com.example.planora.domain.repository.FocusRepository
import com.example.planora.domain.repository.PreferencesRepository
import com.example.planora.domain.repository.TagRepository
import com.example.planora.domain.repository.TaskRepository
import com.example.planora.domain.repository.TaskTemplateRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindTaskRepository(
        taskRepositoryImpl: TaskRepositoryImpl
    ): TaskRepository

    @Binds
    @Singleton
    abstract fun bindCategoryRepository(
        categoryRepositoryImpl: CategoryRepositoryImpl
    ): CategoryRepository

    @Binds
    @Singleton
    abstract fun bindTagRepository(
        tagRepositoryImpl: TagRepositoryImpl
    ): TagRepository

    @Binds
    @Singleton
    abstract fun bindTaskTemplateRepository(
        taskTemplateRepositoryImpl: TaskTemplateRepositoryImpl
    ): TaskTemplateRepository

    @Binds
    @Singleton
    abstract fun bindFocusRepository(
        focusRepositoryImpl: FocusRepositoryImpl
    ): FocusRepository

    @Binds
    @Singleton
    abstract fun bindPreferencesRepository(
        preferencesRepositoryImpl: PreferencesRepositoryImpl
    ): PreferencesRepository

    @Binds
    @Singleton
    abstract fun bindBackupRestoreRepository(
        backupRestoreRepositoryImpl: BackupRestoreRepositoryImpl
    ): BackupRestoreRepository
}
