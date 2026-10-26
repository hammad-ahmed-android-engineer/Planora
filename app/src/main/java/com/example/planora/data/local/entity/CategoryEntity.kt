package com.example.planora.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "categories")
data class CategoryEntity(
    @PrimaryKey
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val description: String = "",
    val colorHex: String = "#4285F4",
    val iconName: String = "folder",
    val createdTimestamp: Long = System.currentTimeMillis(),
    val isArchived: Boolean = false,
    val deadline: Long? = null
)
