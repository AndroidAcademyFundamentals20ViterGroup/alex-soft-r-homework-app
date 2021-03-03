package com.s0l.movies.data.network.response

import com.s0l.movies.data.model.dto.MovieDto
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class DiscoverMovieResponse(
    val page: Int,
    val results: List<MovieDto>,
    val total_pages: Int,
    val total_results: Int
)
