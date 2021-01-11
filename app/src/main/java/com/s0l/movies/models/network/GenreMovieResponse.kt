package com.s0l.movies.models.network

import com.s0l.movies.models.Genre


data class GenreMovieResponse(
    val genres: List<Genre>
)