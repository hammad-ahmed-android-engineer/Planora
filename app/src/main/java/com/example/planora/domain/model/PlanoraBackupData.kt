package com.example.planora.domain.model

/**
 * Domain model representing a full Planora application backup payload.
 */
data class PlanoraBackupData(
    val version: Int = 1,
    val exportedAt: Long = System.currentTimeMillis(),
    val tasks: List<Task> = emptyList(),
    val categories: List<Category> = emptyList(),
    val tags: List<Tag> = emptyList(),
    val templates: List<TaskTemplate> = emptyList()
)
