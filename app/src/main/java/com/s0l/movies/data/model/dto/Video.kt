package com.s0l.movies.data.model.dto

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Video(
    val id: String,
    val name: String,
    val iso_639_1: String,
    val iso_3166_1: String,
    val site: String,
    val key: String,
    val size: Int,
    val type: String
)
