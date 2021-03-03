package com.s0l.movies.data.network.response

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ErrorResponse(
    val status_message: String,
    val status_code: String
)