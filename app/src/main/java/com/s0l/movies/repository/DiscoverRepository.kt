package com.s0l.movies.repository

import com.s0l.movies.api.DiscoverService
import com.s0l.movies.api.GenreService
import com.s0l.movies.api.MovieDetailService
import com.s0l.movies.models.Genre
import com.s0l.movies.models.entity.Movie
import com.s0l.movies.models.network.DiscoverMovieResponse
import com.s0l.movies.models.network.GenreMovieResponse
import com.s0l.movies.ui.movies_list.LoadingState
import com.s0l.movies.ui.movies_list.MovesIsLoaded
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DiscoverRepository @Inject constructor(
    private val discoverService: DiscoverService,
    private val movieDetailService: MovieDetailService,
    private val genreService: GenreService
) : BaseRepository() {

    init {
        Timber.d("Injection DiscoverRepository")
    }

    private var genresMap: Map<Int, Genre>? = null

    private suspend fun loadGenres(): LoadingState {
        return safeApiCall(
            call = { genreService.fetchGenreMovie() },
            errorMessage = "Error fetching Movies"
        )
    }

    suspend fun loadMovies(page: Int): LoadingState {
        val genresData = loadGenres()
        if (genresData is MovesIsLoaded) {
            val genres = (genresData.data as GenreMovieResponse).genres
            genresMap = genres.associateBy { it.id }
        }
        val retrievedData = safeApiCall(
            call = { discoverService.fetchDiscoverMovie(page = page) },
            errorMessage = "Error fetching Movies"
        )
        if (retrievedData is MovesIsLoaded) {
            val moviesList = (retrievedData.data as DiscoverMovieResponse).results
            moviesList.forEach { movie ->
                safeApiCall(
                    call = { movieDetailService.fetchMovieDetails(movie.id) },
                    errorMessage = ""
                ).apply {
                    //Add very important info about Actors&Crew and movies length
                    if (this is MovesIsLoaded) {
                        val _movie = this.data as Movie
                        movie.credits = _movie.credits
                        movie.runtime = _movie.runtime
                    }
                }
                //Add very important info about movies Genres
                genresMap?.let { _genresMap ->
                    movie.genres = movie.genre_ids.map { id ->
                        _genresMap[id] ?: Genre(-1000, "")
                    }
                }
            }

            return MovesIsLoaded(moviesList)
        }
        return retrievedData
    }
}
