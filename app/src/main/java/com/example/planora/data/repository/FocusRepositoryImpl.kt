package com.example.planora.data.repository

import com.example.planora.data.local.dao.FocusSessionDao
import com.example.planora.data.local.entity.toDomain
import com.example.planora.data.local.entity.toEntity
import com.example.planora.domain.model.FocusSession
import com.example.planora.domain.repository.FocusRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDate
import java.time.ZoneId

import javax.inject.Inject

class FocusRepositoryImpl @Inject constructor(
    private val focusDao: FocusSessionDao
) : FocusRepository {

    override fun getAllFocusSessions(): Flow<List<FocusSession>> {
        return focusDao.getAllFocusSessions().map { list -> list.map { it.toDomain() } }
    }

    override fun getTotalFocusMinutesToday(): Flow<Int> {
        val todayStartMs = LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
        return focusDao.getTotalFocusMinutesSince(todayStartMs).map { it ?: 0 }
    }

    override fun getTotalFocusMinutesThisWeek(): Flow<Int> {
        val weekStartMs = LocalDate.now().minusDays(7).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
        return focusDao.getTotalFocusMinutesSince(weekStartMs).map { it ?: 0 }
    }

    override suspend fun insertFocusSession(session: FocusSession) {
        focusDao.insertFocusSession(session.toEntity())
    }
}
