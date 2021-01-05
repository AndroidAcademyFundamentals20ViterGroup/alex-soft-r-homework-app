package com.s0l.movies.movies_list

import android.app.Application
import androidx.lifecycle.*
import com.s0l.movies.data.Movie
import com.s0l.movies.data.loadMovies
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

//TODO remove context from VM, very bad way....
class FragmentMoviesListViewModel (application: Application): AndroidViewModel(application) {

    private val loadingStateLiveData = MutableLiveData<LoadingState>()
    fun getLoadingStageLiveData(): LiveData<out LoadingState> = loadingStateLiveData
    private var moviesData: List<Movie> = listOf()

    fun loadMovies(){
        loadingStateLiveData.value = MovesIsLoading(true)
        viewModelScope.launch(Main) {
            delay(1500)//loading simulating
            withContext(IO) {
                moviesData = loadMovies(getApplication())
            }
            loadingStateLiveData.value = MovesIsLoading(false)
            loadingStateLiveData.value = MovesIsLoaded(moviesData)
        }
    }

}