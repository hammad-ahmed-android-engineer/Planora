package com.example.planora.presentation.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.planora.domain.model.UserPreferences
import com.example.planora.domain.usecase.ExportBackupUseCase
import com.example.planora.domain.usecase.ImportRestoreUseCase
import com.example.planora.domain.usecase.ManagePreferencesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val managePreferencesUseCase: ManagePreferencesUseCase,
    private val exportBackupUseCase: ExportBackupUseCase,
    private val importRestoreUseCase: ImportRestoreUseCase
) : ViewModel() {

    val userPreferences: StateFlow<UserPreferences> = managePreferencesUseCase.userPreferencesFlow
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = UserPreferences()
        )

    fun updateThemeMode(mode: String) {
        viewModelScope.launch { managePreferencesUseCase.updateThemeMode(mode) }
    }

    fun updateAccentColor(colorHex: String) {
        viewModelScope.launch { managePreferencesUseCase.updateAccentColor(colorHex) }
    }

    fun updateCompactDensity(enabled: Boolean) {
        viewModelScope.launch { managePreferencesUseCase.updateCompactDensity(enabled) }
    }

    fun updateNotificationsEnabled(enabled: Boolean) {
        viewModelScope.launch { managePreferencesUseCase.updateNotificationsEnabled(enabled) }
    }

    fun updateNotificationStyle(style: String) {
        viewModelScope.launch { managePreferencesUseCase.updateNotificationStyle(style) }
    }

    suspend fun exportJson(): String {
        return exportBackupUseCase.createJsonBackup()
    }

    suspend fun importJson(json: String): Result<Int> {
        return importRestoreUseCase.restoreFromJson(json)
    }

    fun clearAllData() {
        viewModelScope.launch {
            managePreferencesUseCase.clearAllPreferences()
        }
    }
}
