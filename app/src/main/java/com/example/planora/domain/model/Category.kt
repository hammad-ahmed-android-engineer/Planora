package com.example.planora.domain.model

import java.util.UUID

data class Category(
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val description: String = "",
    val colorHex: String = "#4285F4",
    val iconName: String = "folder",
    val createdTimestamp: Long = System.currentTimeMillis(),
    val isArchived: Boolean = false,
    val deadline: Long? = null,
    val taskCount: Int = 0,
    val completedTaskCount: Int = 0
)
