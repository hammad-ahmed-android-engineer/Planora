package com.example.planora.domain.model

import java.util.UUID

data class Reminder(
    val id: String = UUID.randomUUID().toString(),
    val taskId: String = "",
    val triggerTimestamp: Long,
    val leadTimeMinutes: Int = 0,
    val isEnabled: Boolean = true,
    val notificationId: Int = (System.currentTimeMillis() % Int.MAX_VALUE).toInt()
)
