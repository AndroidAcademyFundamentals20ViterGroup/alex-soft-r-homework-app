package com.s0l.movies.movies_list

import android.content.Context
import com.s0l.movies.data.Movie
import com.s0l.movies.data.loadMovies
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class MovieInteractor(val context: Context, val dispatcher: CoroutineDispatcher) {
    suspend fun getDataMovies() : List<Movie> = withContext(dispatcher){
        loadMovies(context = context)
    }
}