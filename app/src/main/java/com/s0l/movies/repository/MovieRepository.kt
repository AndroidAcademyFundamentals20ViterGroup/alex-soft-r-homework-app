package com.s0l.movies.repository

import com.s0l.movies.api.MovieDetailService
import com.s0l.movies.models.entity.Movie
import com.skydoves.sandwich.ApiResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieRepository @Inject constructor(
    private val movieDetailService: MovieDetailService
) {

    init {
        Timber.d("Injection MovieRepository")
    }

    suspend fun loadKeywordList(id: Int): Response<Movie> {
        return movieDetailService.fetchMovieDetails(id = id)
    }

}
