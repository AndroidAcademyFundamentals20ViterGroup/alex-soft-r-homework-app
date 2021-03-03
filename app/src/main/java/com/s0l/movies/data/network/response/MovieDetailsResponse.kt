package com.s0l.movies.data.network.response

import com.s0l.movies.data.model.dto.CreditsDto
import com.s0l.movies.data.model.dto.GenreDto
import com.s0l.movies.data.model.dto.SpokenLanguages
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MovieDetailsResponse(
    val id: Int = 0,
    val adult: Boolean = false,
    val overview: String = "",
    val release_date: String = "",
    val original_title: String = "",
    val original_language: String = "",
    val title: String = "",
    val backdrop_path: String? = null,
    val popularity: Float = 0f,
    val vote_average: Float = 0f,
    val video: Boolean = false,
    val vote_count: Int = 0,
    val poster_path: String? = null,
    var runtime: Int = 0,
    var genres: List<GenreDto> = listOf(),
    var videos: VideoDetailsResponse? = null,
    var reviews: ReviewsResponse? = null,//only firs pages will be used
    var credits: CreditsDto? = null,
    val homepage: String? = null,
    val imdb_id: String? = null,
    val status: String = "",//Allowed Values: Rumored, Planned, In Production, Post Production, Released, Canceled
    val tagline: String? = null,
    val spoken_languages: List<SpokenLanguages> = listOf(),
//    val author_details: @RawValue AuthorDetails? = null,
)
{
    @JsonClass(generateAdapter = true)
    data class AuthorDetails(
        val name: String,
        val username: String,
        val avatar_path: String,
        val rating: Float
    )
}