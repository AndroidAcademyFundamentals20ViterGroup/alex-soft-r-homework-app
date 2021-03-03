package com.s0l.movies.data.network.response

import com.s0l.movies.data.model.dto.Video
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class VideoDetailsResponse(
    val results: List<Video>,
)
