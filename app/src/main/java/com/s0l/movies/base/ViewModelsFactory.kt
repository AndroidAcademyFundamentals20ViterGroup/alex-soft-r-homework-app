package com.s0l.movies.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.s0l.movies.movies_list.MovieInteractor

class ViewModelsFactory(val interactor: MovieInteractor) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(MovieInteractor::class.java).newInstance(interactor)
    }
}