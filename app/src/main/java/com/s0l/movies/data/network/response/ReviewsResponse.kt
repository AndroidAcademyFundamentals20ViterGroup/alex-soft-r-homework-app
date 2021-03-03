package com.s0l.movies.data.network.response


import com.s0l.movies.data.model.dto.Review
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ReviewsResponse(
    val results: List<Review>? = listOf(),
    val page: String,
    val total_pages: String,
    val total_results: String
)

