package com.s0l.movies.models.network

import com.s0l.movies.models.entity.Movie
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class DiscoverMovieResponse(
  val page: Int,
  val results: List<Movie>,
  val total_pages: Int,
  val total_results: Int
)
