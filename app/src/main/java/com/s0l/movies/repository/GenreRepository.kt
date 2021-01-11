package com.s0l.movies.repository

import com.s0l.movies.api.GenreService
import com.s0l.movies.models.network.GenreMovieResponse
import com.skydoves.sandwich.ApiResponse
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GenreRepository @Inject constructor(
    private val genreService: GenreService
) : Repository {

    init {
        Timber.d("Injection DiscoverRepository")
    }

    suspend fun loadGenres(): ApiResponse<GenreMovieResponse>  {
        return genreService.fetchGenreMovie()
    }
}
