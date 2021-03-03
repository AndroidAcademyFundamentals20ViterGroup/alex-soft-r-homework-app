package com.s0l.movies.data.usecases.base

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

abstract class BaseUseCases {

    protected val dispatcher: CoroutineDispatcher = Dispatchers.IO

}