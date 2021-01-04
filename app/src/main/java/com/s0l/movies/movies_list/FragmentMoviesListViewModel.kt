package com.s0l.movies.movies_list

import androidx.lifecycle.ViewModel
import com.s0l.movies.models.MovieData
import com.s0l.movies.data.MoviesDataSource

class FragmentMoviesListViewModel : ViewModel() {

    fun getMovies(): List<MovieData> {
        return MoviesDataSource().getMovies()
    }

}