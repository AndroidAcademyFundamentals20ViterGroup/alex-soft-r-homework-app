package com.s0l.movies.data.model.entity

import androidx.room.PrimaryKey

data class VideoEntity(
    @PrimaryKey(autoGenerate = true)
    val pk: Int = 0,
    val id: String,
    val name: String,
    val iso_639_1: String,
    val iso_3166_1: String,
    val site: String,
    val key: String,
    val size: Int,
    val type: String
)
