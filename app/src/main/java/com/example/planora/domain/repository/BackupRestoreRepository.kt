package com.example.planora.domain.repository

import android.content.Context
import android.net.Uri

/**
 * Domain repository interface for data backup creation, export, and restoration.
 */
interface BackupRestoreRepository {
    suspend fun createJsonBackup(): String
    suspend fun exportToCsv(): String
    suspend fun exportData(context: Context, uri: Uri): Result<Unit>
    suspend fun importData(context: Context, uri: Uri): Result<Int>
    suspend fun restoreFromJsonBackup(jsonString: String): Result<Int>
}
