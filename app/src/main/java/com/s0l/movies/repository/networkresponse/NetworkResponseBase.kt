package com.s0l.movies.repository.networkresponse

import okio.IOException

typealias NetworkResponse<S> = NetworkResponseBase<S, Error>

sealed class NetworkResponseBase<out T : Any, out U : Any> {
    /**
     * Success response with the body of the success state of the request
     */
    data class Success<T : Any>(val body: T) : NetworkResponseBase<T, Nothing>()

    /**
     * Failure response with body, non-2xx responses
     */
    data class ApiError<U : Any>(val body: U, val code: Int) : NetworkResponseBase<Nothing, U>()

    /**
     * Network error, such as no internet connection cases
     */
    data class NetworkError(val error: IOException) : NetworkResponseBase<Nothing, Nothing>()

    /**
     * For example, json parsing error, unexpected exceptions
     */
    data class UnknownError(val error: Throwable?) : NetworkResponseBase<Nothing, Nothing>()
}
