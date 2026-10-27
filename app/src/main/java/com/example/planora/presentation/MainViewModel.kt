package com.example.planora.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.planora.domain.model.UserPreferences
import com.example.planora.domain.usecase.ManagePreferencesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

data class MainUiState(
    val userPreferences: UserPreferences = UserPreferences(),
    val navigateTaskId: String? = null
)

@HiltViewModel
class MainViewModel @Inject constructor(
    managePreferencesUseCase: ManagePreferencesUseCase,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    companion object {
        private const val KEY_NAVIGATE_TASK_ID = "extra_navigate_task_id"
    }

    val userPreferences: StateFlow<UserPreferences> = managePreferencesUseCase.userPreferencesFlow
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = UserPreferences()
        )

    val navigateTaskId: StateFlow<String?> = savedStateHandle.getStateFlow(KEY_NAVIGATE_TASK_ID, null)

    fun setNavigateTaskId(taskId: String?) {
        savedStateHandle[KEY_NAVIGATE_TASK_ID] = taskId
    }

    fun onTaskNavigated() {
        savedStateHandle[KEY_NAVIGATE_TASK_ID] = null
    }
}
