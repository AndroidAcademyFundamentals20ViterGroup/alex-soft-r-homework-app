package com.s0l.movies.data.network.response

import com.s0l.movies.data.model.dto.GenreDto
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GenreMovieResponse(
    @Json(name = "genres")
    val genreDto: List<GenreDto>
)