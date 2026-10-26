package com.example.planora.domain.model

import java.util.UUID

data class TaskTemplate(
    val id: String = UUID.randomUUID().toString(),
    val title: String,
    val description: String = "",
    val priority: Priority = Priority.NONE,
    val categoryId: String? = null,
    val relativeDueDays: Int = 0,
    val colorHex: String? = null,
    val defaultSubtasks: List<String> = emptyList()
)
