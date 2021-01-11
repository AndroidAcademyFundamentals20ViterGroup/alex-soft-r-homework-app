package com.s0l.movies.repository

import androidx.annotation.WorkerThread
import com.s0l.movies.api.DiscoverService
import com.s0l.movies.api.MovieDetailService
import com.s0l.movies.models.entity.Movie
import com.s0l.movies.models.network.DiscoverMovieResponse
import com.s0l.movies.ui.movies_list.MovesIsLoaded
import com.skydoves.sandwich.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DiscoverRepository @Inject constructor(
    private val discoverService: DiscoverService,
    private val movieDetailService: MovieDetailService
) : Repository {

    init {
        Timber.d("Injection DiscoverRepository")
    }

    suspend fun loadMovies(page: Int): ApiResponse<DiscoverMovieResponse> {
        return discoverService.fetchDiscoverMovie(page = page)
    }

    @WorkerThread
    suspend fun loadMovies2(
        page: Int,
        onSuccess: (List<Movie>?) -> Unit,
        onError: (String) -> Unit
    )  {
        discoverService.fetchDiscoverMovie(page = page).apply {
            // handle the case when the API request gets a success response.
            this.suspendOnSuccess {
                val movies = data?.results
                movies?.let {
                    it.forEach { movie ->
                        movieDetailService.fetchMovieDetails(movie.id).apply {
                            this.suspendOnSuccess {
                                movie.credits = data?.credits
                                data?.runtime?.let {
                                    movie.runtime = data?.runtime!!
                                }
                            }
                            //Skip error handling
                        }
                    }
                }
                onSuccess(movies)
            }
            // handle the case when the API request gets an error response.
            // e.g., internal server error.
            .onError {
                onError("[Code: ${statusCode.code}]: ${message()}")
            }
            // handle the case when the API request gets an exception response.
            // e.g., network connection error.
            .onException {
                onError(message())
            }
        }
    }
}
