package com.s0l.movies.ui.movies_list

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.s0l.movies.data.model.entity.MovieEntity
import com.s0l.movies.data.usecases.network.MovieNetworkInteractor
import com.s0l.movies.ui.base.BaseViewModel
import kotlinx.coroutines.flow.Flow


@ExperimentalPagingApi
class FragmentMoviesListViewModel @ViewModelInject constructor(
    private val movieInteractor: MovieNetworkInteractor,
) : BaseViewModel<PagingData<MovieEntity>>() {

    private lateinit var _moviessFlow: Flow<PagingData<MovieEntity>>
    val moviesFlow: Flow<PagingData<MovieEntity>>
        get() = _moviessFlow

    init {
        loadMovies()
    }

    @ExperimentalPagingApi
    private fun loadMovies() = launchPagingAsync({
        movieInteractor.getMoviesFlow().cachedIn(viewModelScope)
    }, {
        _moviessFlow = it
    })

//    fun loadMovies(): Flow<PagingData<MovieEntity>> {
//        return movieInteractor.getMoviesFlow().cachedIn(viewModelScope)
//    }

}