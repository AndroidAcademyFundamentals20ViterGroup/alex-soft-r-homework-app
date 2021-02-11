package com.s0l.movies.ui.movies_list

sealed class LoadingState

class MovesIsLoading(var showProgress: Boolean) : LoadingState()
class MovesIsLoaded(val data: Any): LoadingState()
class MovesLoadingError(val exception: String) : LoadingState()