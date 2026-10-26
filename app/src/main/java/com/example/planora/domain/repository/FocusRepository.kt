package com.example.planora.domain.repository

import com.example.planora.domain.model.FocusSession
import kotlinx.coroutines.flow.Flow

interface FocusRepository {
    fun getAllFocusSessions(): Flow<List<FocusSession>>
    fun getTotalFocusMinutesToday(): Flow<Int>
    fun getTotalFocusMinutesThisWeek(): Flow<Int>
    suspend fun insertFocusSession(session: FocusSession)
}
