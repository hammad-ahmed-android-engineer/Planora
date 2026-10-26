package com.example.planora.data.preferences

import com.example.planora.data.repository.PreferencesRepositoryImpl
import com.example.planora.domain.model.UserSettings

/**
 * Backward compatibility alias for [PreferencesRepositoryImpl] and [UserSettings].
 */
typealias UserSettings = com.example.planora.domain.model.UserSettings
typealias UserPreferencesRepository = PreferencesRepositoryImpl
