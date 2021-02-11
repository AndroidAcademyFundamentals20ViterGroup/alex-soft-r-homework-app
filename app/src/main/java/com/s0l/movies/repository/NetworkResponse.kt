package com.s0l.movies.repository

sealed class NetworkResponse<out T> {
    /**
     * Success response with the body of the success state of the request
     */
    data class Success<out T>(val data: T) : NetworkResponse<T>()

    /**
     * Failure response
     */
    data class ApiError(val exception: Exception) : NetworkResponse<Nothing>()
}