package com.s0l.movies.ui.movies_list

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.s0l.movies.models.Genre
import com.s0l.movies.repository.DiscoverRepository
import com.s0l.movies.repository.GenreRepository
import com.s0l.movies.repository.MovieRepository
import com.skydoves.sandwich.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import timber.log.Timber

class FragmentMoviesListViewModel @ViewModelInject constructor(
    private val discoverRepository: DiscoverRepository,
    private val genreRepository: GenreRepository,
    private val moveRepository: MovieRepository
) : ViewModel() {

//    private var moviePageLiveData: MutableLiveData<Int> = MutableLiveData()

    private var loadingStateLiveData = MutableLiveData<LoadingState>()
    fun getLoadingStageLiveData(): LiveData<out LoadingState> = loadingStateLiveData
//    val loadingStateLiveData2: LiveData<LoadingState>

    private var genresMap: Map<Int, Genre>? = null

    private fun setLoading(loading: Boolean){
        loadingStateLiveData.postValue(MovesIsLoading(loading))
    }

    fun isLoading(): Boolean{
        return loadingStateLiveData.value is MovesIsLoading
    }

    fun loadGenres() {
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
        }
    }

    fun loadMovies2(page: Int) {
        setLoading(true)
        viewModelScope.launch(IO) {
            discoverRepository.loadMovies2(page = page,
                onSuccess = {
                    setLoading(false)
                    loadingStateLiveData.postValue(MovesIsLoaded(it!!))
                },
                onError = {                     //API got exception
                    Timber.d(it)
                    setLoading(false)
                    loadingStateLiveData.postValue(MovesLoadingError(it))
                }
            )
        }
    }

    /*fun loadMovies(page: Int) {
        loadingStateLiveData.value = MovesIsLoading(true)
        viewModelScope.launch(IO) {
            discoverRepository.loadMovies(page = page)
                .onSuccess {
                    setLoading(false)
                    val moves = data?.results!!
                    genresMap?.let { _genresMap ->
                        moves.forEach { movie ->
                            movie.genres = movie.genre_ids.map { id ->
                                _genresMap[id] ?: throw IllegalArgumentException("Genre not found")
                            }
                        }
                    }

                    loadingStateLiveData.postValue(MovesIsLoaded(moves))
                }
                .onError {
                    //API error
                    Timber.d(message())
                    setLoading(false)
                    when (statusCode) {
                        StatusCode.InternalServerError -> loadingStateLiveData.postValue(
                            MovesLoadingError("InternalServerError")
                        )
                        StatusCode.BadGateway -> loadingStateLiveData.postValue(MovesLoadingError("BadGateway"))
                        else -> loadingStateLiveData.postValue(MovesLoadingError("$statusCode(${statusCode.code}): ${message()}"))
                    }
                }
                .onException {
                    //API got exception
                    Timber.d(message())
                    setLoading(false)
                    loadingStateLiveData.postValue(MovesLoadingError(message()))
                }
        }
    }*/

}