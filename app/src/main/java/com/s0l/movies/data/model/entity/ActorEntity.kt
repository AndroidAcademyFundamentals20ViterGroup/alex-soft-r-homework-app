package com.s0l.movies.data.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "actors")
data class ActorEntity(
    @PrimaryKey(autoGenerate = true)
    val pk: Int = 0,
    val id: Int = 0,
    val adult: Boolean = false,
    val gender: Int? = null,
    val known_for_department: String = "",
    val name: String = "",
    val original_name: String = "",
    val popularity: Float = 0f,
    val profile_path: String? = null,
    val cast_id: Int = 0,
    val character: String = "",
    val credit_id: String = "",
    val order: Int = 0,
)