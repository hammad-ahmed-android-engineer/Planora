package com.example.planora.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "focus_sessions")
data class FocusSessionEntity(
    @PrimaryKey
    val id: String = UUID.randomUUID().toString(),
    val taskId: String? = null,
    val startTimestamp: Long = System.currentTimeMillis(),
    val endTimestamp: Long = System.currentTimeMillis(),
    val durationMinutes: Int = 25,
    val sessionType: String = "FOCUS", // FOCUS, SHORT_BREAK, LONG_BREAK
    val isCompleted: Boolean = true
)
