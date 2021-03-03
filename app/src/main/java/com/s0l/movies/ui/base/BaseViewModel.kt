package com.s0l.movies.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.s0l.movies.utils.SingleLiveEvent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

open class BaseViewModel<T> : ViewModel() {

    protected val mutableStateFlow: MutableStateFlow<LoadingState<T>> =
        MutableStateFlow(LoadingState.MovesIsLoading)

    protected fun requestWithStateFlow(request: suspend () -> Flow<T>, ) {
        viewModelScope.launch {
            try {
                mutableStateFlow.value = LoadingState.MovesIsLoading
//                delay(2000L)
                val response = request.invoke()
                response.collectLatest {
                    mutableStateFlow.value = LoadingState.MovesIsLoaded(it)
                }
            } catch (e: Exception) {
                mutableStateFlow.value = LoadingState.MovesLoadingError(e)
            }
        }
    }

    protected fun requestWithStateFlowFromResult(request: () -> Flow<LoadingState<T>>) {
        viewModelScope.launch {
            val response = request.invoke()
            response.collect {
                mutableStateFlow.value = it
            }
        }
    }

    var errorMessage = SingleLiveEvent<String>()

    inline fun <T> launchPagingAsync(
        crossinline execute: suspend () -> Flow<T>,
        crossinline onSuccess: (Flow<T>) -> Unit
    ) {
        viewModelScope.launch {
            try {
                val result = execute()
                onSuccess(result)
            } catch (ex: Exception) {
                errorMessage.value = ex.message
            }
        }
    }
}