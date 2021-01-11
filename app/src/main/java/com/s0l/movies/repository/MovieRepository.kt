package com.s0l.movies.repository

import com.s0l.movies.api.MovieDetailService
import com.s0l.movies.models.entity.Movie
import com.skydoves.sandwich.ApiResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieRepository @Inject constructor(
    private val movieDetailService: MovieDetailService
) : Repository {

    init {
        Timber.d("Injection MovieRepository")
    }

/*    suspend fun loadKeywordList(id: Int): NetworkResponse<KeywordListResponse> {
        return movieService.fetchKeywords(id = id)
    }  */

    suspend fun loadMovieDetails(id: Int): ApiResponse<Movie> = withContext(Dispatchers.IO) {
        return@withContext movieDetailService.fetchMovieDetails(id = id)
    }

}
