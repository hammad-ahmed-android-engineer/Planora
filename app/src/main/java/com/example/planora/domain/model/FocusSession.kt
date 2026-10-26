package com.example.planora.domain.model

import java.util.UUID

data class FocusSession(
    val id: String = UUID.randomUUID().toString(),
    val taskId: String? = null,
    val startTimestamp: Long = System.currentTimeMillis(),
    val endTimestamp: Long = System.currentTimeMillis(),
    val durationMinutes: Int = 25,
    val sessionType: String = "FOCUS",
    val isCompleted: Boolean = true
)
