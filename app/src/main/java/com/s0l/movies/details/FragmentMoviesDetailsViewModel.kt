package com.s0l.movies.details

import androidx.lifecycle.ViewModel
import com.s0l.movies.data.MoviesDataSource
import com.s0l.movies.models.MovieData

class FragmentMoviesDetailsViewModel : ViewModel() {
    fun getMovies(): List<MovieData> {
        return MoviesDataSource().getMovies()
    }
}