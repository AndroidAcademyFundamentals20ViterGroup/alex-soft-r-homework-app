package com.s0l.movies.data.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "crew")
data class CrewEntity(
    @PrimaryKey(autoGenerate = true)
    val pk: Int = 0,
    val id: Int,
    val adult: Boolean,
    val gender: Int?,
    val known_for_department: String = "",
    val name: String = "",
    val original_name: String = "",
    val popularity: Float,
    val profile_path: String?,
    val credit_id: String = "",
    val department: String = "",
    val job: String = "",
)