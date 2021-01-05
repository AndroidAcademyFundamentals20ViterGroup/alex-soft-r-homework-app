package com.s0l.movies.movies_list

import androidx.lifecycle.*
import com.s0l.movies.data.Movie
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

//TODO remove context from VM, very bad way....
class FragmentMoviesListViewModel(private val interactor: MovieInteractor) : ViewModel() {

    private val loadingStateLiveData = MutableLiveData<LoadingState>()
    fun getLoadingStageLiveData(): LiveData<out LoadingState> = loadingStateLiveData
    private var moviesData: List<Movie> = listOf()

    fun loadMovies(){
        loadingStateLiveData.value = MovesIsLoading(true)
        viewModelScope.launch(Main) {
            delay(1500)//loading simulating
            moviesData = interactor.getDataMovies()
            loadingStateLiveData.value = MovesIsLoading(false)
            loadingStateLiveData.value = MovesIsLoaded(moviesData)
        }
    }

}