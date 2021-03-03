package com.s0l.movies.ui.base

sealed class LoadingState<out T> {
    object MovesIsLoading : LoadingState<Nothing>()
    data class MovesIsLoaded<T>(val data: T) : LoadingState<T>()
    data class MovesLoadingError(val exception: Throwable) : LoadingState<Nothing>()
}