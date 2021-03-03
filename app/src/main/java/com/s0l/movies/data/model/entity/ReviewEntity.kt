package com.s0l.movies.data.model.entity

import androidx.room.PrimaryKey

data class ReviewEntity(
    @PrimaryKey(autoGenerate = true)
    val pk: Int = 0,
    val id: String,
    val author: String,
    val content: String,
    val url: String,
)


