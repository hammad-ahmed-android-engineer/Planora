package com.example.planora.domain.usecase

import android.content.Context
import android.net.Uri
import com.example.planora.domain.repository.BackupRestoreRepository
import javax.inject.Inject

/**
 * UseCase to handle JSON data backup creation.
 */
class ExportBackupUseCase @Inject constructor(
    private val backupRestoreRepository: BackupRestoreRepository
) {
    suspend operator fun invoke(context: Context, uri: Uri): Result<Unit> {
        return backupRestoreRepository.exportData(context, uri)
    }

    suspend fun createJsonBackup(): String {
        return backupRestoreRepository.createJsonBackup()
    }

    suspend fun exportToCsv(): String {
        return backupRestoreRepository.exportToCsv()
    }
}

/**
 * UseCase to handle JSON data backup restoration.
 */
class ImportRestoreUseCase @Inject constructor(
    private val backupRestoreRepository: BackupRestoreRepository
) {
    suspend operator fun invoke(context: Context, uri: Uri): Result<Int> {
        return backupRestoreRepository.importData(context, uri)
    }

    suspend fun restoreFromJson(jsonString: String): Result<Int> {
        return backupRestoreRepository.restoreFromJsonBackup(jsonString)
    }
}
