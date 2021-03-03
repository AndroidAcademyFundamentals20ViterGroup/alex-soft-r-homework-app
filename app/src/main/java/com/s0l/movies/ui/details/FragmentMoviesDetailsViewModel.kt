package com.s0l.movies.ui.details

import androidx.hilt.lifecycle.ViewModelInject
import androidx.paging.ExperimentalPagingApi
import com.s0l.movies.data.model.entity.MovieEntity
import com.s0l.movies.data.usecases.network.MovieNetworkInteractor
import com.s0l.movies.ui.base.BaseViewModel
import kotlinx.coroutines.flow.Flow

class FragmentMoviesDetailsViewModel @ViewModelInject constructor(
    private val movieInteractor: MovieNetworkInteractor,
//    private val notifications: MovieNotifications
) : BaseViewModel<MovieEntity>() {

    private lateinit var _moviessFlow: Flow<MovieEntity>
    val moviesFlow: Flow<MovieEntity>
        get() = _moviessFlow

    @ExperimentalPagingApi
    fun loadMovieDetails(id: Int) = launchPagingAsync({
//        notifications.dismiss(movieId)
        movieInteractor.getMovieById(id)
    }, {
        _moviessFlow = it
    })
}