package com.s0l.movies.data.model.dto

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Review(
    val id: String,
    val author: String,
    val content: String,
    val url: String,
)


