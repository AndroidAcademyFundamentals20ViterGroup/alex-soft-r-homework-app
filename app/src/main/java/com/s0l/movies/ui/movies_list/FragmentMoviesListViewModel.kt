package com.s0l.movies.ui.movies_list

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.s0l.movies.repository.DiscoverRepository
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class FragmentMoviesListViewModel @ViewModelInject constructor(
    private val discoverRepository: DiscoverRepository,
) : ViewModel() {

    private var loadingStateLiveData = MutableLiveData<LoadingState>()
    fun getLoadingStageLiveData(): LiveData<out LoadingState> = loadingStateLiveData

    private fun setLoading(loading: Boolean) {
        loadingStateLiveData.postValue(MovesIsLoading(loading))
    }

    fun isLoading(): Boolean {
        return loadingStateLiveData.value is MovesIsLoading
    }

/*    fun loadGenres2() = liveData(IO) {
        emit(MovesIsLoading(true))
        val retrievedData = genreRepository.loadGenres2()
        if (retrievedData is MovesIsLoaded) {
            val genres = (retrievedData.data as GenreMovieResponse).genres
            genresMap = genres.associateBy { it.id }
        }
        emit(value = retrievedData)
    }*/

/*    fun loadGenres() {
        viewModelScope.launch(IO) {
            genreRepository.loadGenres()
                .onSuccess {
                    setLoading(false)
                    genresMap = data?.genres?.associateBy { it.id }
                    loadingStateLiveData.postValue(MovesIsLoaded(data!!))
                }.onError {
                    //API got error
                    Timber.d(message())
                    setLoading(false)
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
                    setLoading(false)
                    loadingStateLiveData.postValue(MovesLoadingError(message()))
                }

    }}*/


    fun loadMovies2(page: Int) {
        setLoading(true)
        viewModelScope.launch(IO) {
            val retrievedData = discoverRepository.loadMovies(page = page)
            setLoading(false)
            loadingStateLiveData.postValue(retrievedData)
        }
    }

}