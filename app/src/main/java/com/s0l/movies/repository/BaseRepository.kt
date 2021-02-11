package com.s0l.movies.repository

import com.s0l.movies.ui.movies_list.LoadingState
import com.s0l.movies.ui.movies_list.MovesIsLoaded
import com.s0l.movies.ui.movies_list.MovesLoadingError
import okio.IOException
import retrofit2.Response
import timber.log.Timber

open class BaseRepository {

    suspend fun <T : Any> safeApiCall(call: suspend () -> Response<T>, errorMessage: String): LoadingState {

        val networkResponse: NetworkResponse<T> = safeApiNetworkResponse(call)

        return when (networkResponse) {
            is NetworkResponse.Success -> {
                MovesIsLoaded(networkResponse.data)
            }
            is NetworkResponse.ApiError -> {
                Timber.d("DataRepository $errorMessage & Exception - ${networkResponse.exception}")
                MovesLoadingError(networkResponse.exception.localizedMessage!!)
            }
        }
    }

    private suspend fun <T : Any> safeApiNetworkResponse(
        call: suspend () -> Response<T>
    ): NetworkResponse<T> {
        val response = call.invoke()
        if (response.isSuccessful)
            return NetworkResponse.Success(response.body()!!)

        return NetworkResponse.ApiError(IOException("IOException code = ${response.code()}"))
    }
}