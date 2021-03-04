package com.s0l.movies.data.usecases.network

import com.s0l.movies.data.model.entity.MovieEntity
import com.s0l.movies.data.room.AppDatabase
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TopRatedMovieInteractor @Inject constructor(
    val appDatabase: AppDatabase,
) {

    private var topRatedMovie: MovieEntity? = null

    suspend fun getNewTopRatedMovie(): MovieEntity? {
        val movies = appDatabase.moviesDao().getAllMovies()
        return movies.maxByOrNull { it.vote_average }?.let { movie ->
            if (
                movie.vote_average > topRatedMovie?.vote_average ?: 0f
                && topRatedMovie?.id != movie.id
            ) {
                topRatedMovie = movie
                topRatedMovie
            } else {
                null
            }
        }
    }

}