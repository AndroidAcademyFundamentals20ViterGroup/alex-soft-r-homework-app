package com.s0l.movies.data.model.entity

import androidx.room.PrimaryKey

data class AuthorDetailsEntity(
    @PrimaryKey(autoGenerate = true)
    val pk: Int = 0,
    val name: String,
    val username: String,
    val avatar_path: String,
    val rating: Float,
)
