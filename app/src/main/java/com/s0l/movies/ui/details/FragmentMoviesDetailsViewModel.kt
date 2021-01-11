package com.s0l.movies.ui.details

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.s0l.movies.repository.MovieRepository
import com.s0l.movies.ui.movies_list.LoadingState
import com.s0l.movies.ui.movies_list.MovesIsLoaded
import com.s0l.movies.ui.movies_list.MovesIsLoading
import com.s0l.movies.ui.movies_list.MovesLoadingError
import com.skydoves.sandwich.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber

class FragmentMoviesDetailsViewModel @ViewModelInject constructor(
    private val moveRepository: MovieRepository
)  : ViewModel() {

/*    private val loadingStateLiveData = MutableLiveData<LoadingState>()
    fun getLoadingStageLiveData(): LiveData<out LoadingState> = loadingStateLiveData

    fun loadMovieDetails(id: Int) {
        viewModelScope.launch(Dispatchers.Main) {
            moveRepository.loadMovieDetails(id)
                .onSuccess {
                    loadingStateLiveData.postValue(MovesIsLoading(false))
                    loadingStateLiveData.postValue(MovesIsLoaded(data!!))
                }.onError {
                    //API error
                    Timber.d(message())
                    loadingStateLiveData.postValue(MovesIsLoading(false))
                    when (statusCode) {
                        StatusCode.InternalServerError -> loadingStateLiveData.postValue(
                            MovesLoadingError("InternalServerError")
                        )
                        StatusCode.BadGateway -> loadingStateLiveData.postValue(MovesLoadingError("BadGateway"))
                        else -> loadingStateLiveData.postValue(MovesLoadingError("$statusCode(${statusCode.code}): ${message()}"))
                    }
                }.onException {
                    //API got exception
                    Timber.d(message())
                    loadingStateLiveData.postValue(MovesIsLoading(false))
                    loadingStateLiveData.postValue(MovesLoadingError(message()))
                }
        }
    }*/
}