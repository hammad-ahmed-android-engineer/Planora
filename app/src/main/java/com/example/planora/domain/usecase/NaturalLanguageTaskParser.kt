package com.example.planora.domain.usecase

import com.example.planora.domain.model.Priority

data class ParsedTaskDetails(
    val title: String,
    val dueDate: String? = null,
    val dueTime: String? = null,
    val priority: Priority = Priority.NONE,
    val tagNames: List<String> = emptyList()
)
