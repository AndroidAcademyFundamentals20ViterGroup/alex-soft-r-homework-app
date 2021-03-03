package com.s0l.movies.data.model.dto

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SpokenLanguages(
    val english_name: String,
    val iso_639_1: String,
    val name: String
)