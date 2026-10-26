package com.example.planora.domain.model

import java.util.UUID

data class Subtask(
    val id: String = UUID.randomUUID().toString(),
    val taskId: String = "",
    val title: String,
    val isCompleted: Boolean = false,
    val sortOrder: Int = 0
)
