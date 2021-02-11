package com.s0l.movies.models.network

import com.s0l.movies.models.Genre
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GenreMovieResponse(
    val genres: List<Genre>
)