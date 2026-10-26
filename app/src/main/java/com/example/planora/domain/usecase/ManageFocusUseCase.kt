package com.example.planora.domain.usecase

import com.example.planora.domain.model.FocusSession
import com.example.planora.domain.repository.FocusRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ManageFocusUseCase @Inject constructor(
    private val focusRepository: FocusRepository
) {
    fun getTotalFocusMinutesToday(): Flow<Int> {
        return focusRepository.getTotalFocusMinutesToday()
    }

    fun getTotalFocusMinutesThisWeek(): Flow<Int> {
        return focusRepository.getTotalFocusMinutesThisWeek()
    }

    suspend fun insertFocusSession(session: FocusSession) {
        focusRepository.insertFocusSession(session)
    }
}
