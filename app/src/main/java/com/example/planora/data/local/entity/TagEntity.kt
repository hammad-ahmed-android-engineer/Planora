package com.example.planora.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.planora.domain.model.Tag
import java.util.UUID

@Entity(tableName = "tags")
data class TagEntity(
    @PrimaryKey
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val colorHex: String = "#34A853"
)

fun TagEntity.toDomain() = Tag(
    id = id,
    name = name,
    colorHex = colorHex
)

fun Tag.toEntity() = TagEntity(
    id = id,
    name = name,
    colorHex = colorHex
)
