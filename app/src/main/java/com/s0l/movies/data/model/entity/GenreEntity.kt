package com.s0l.movies.data.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "genres")
data class GenreEntity(
    @PrimaryKey(autoGenerate = true)
    val pk: Int = 0,
    val id: Int,
    val name: String
)