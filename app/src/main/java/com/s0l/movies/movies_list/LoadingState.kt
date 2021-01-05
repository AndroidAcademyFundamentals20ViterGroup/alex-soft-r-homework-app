package com.s0l.movies.movies_list

import com.s0l.movies.data.Movie

sealed class LoadingState

class MovesIsLoading(var showProgress: Boolean) : LoadingState()
class MovesIsLoaded(val list: List<Movie>): LoadingState()
class MovesLoadingError(val exception: Exception) : LoadingState()
