package com.s0l.movies.models.network

import android.os.Parcelable
import com.s0l.movies.models.Genre
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Deprecated("")
@Parcelize
@JsonClass(generateAdapter = true)
data class MovieDetailsResponse(
    val id: Int,
    var page: Int, //!
    val poster_path: String?,
    val adult: Boolean = false,
    val overview: String,
    val release_date: String,
    val genre_ids: List<Int>,
    val original_title: String,
    val original_language: String,
    val title: String,
    val backdrop_path: String?,
    val popularity: Float,
    val vote_average: Float,
    val video: Boolean,
    val vote_count: Int,
    val runtime: Int = 0,
    var genres: @RawValue List<Genre> = ArrayList(),
    //var videos: List<Video>? = ArrayList(),
    //var reviews: List<Review>? = ArrayList(),
    var credits: @RawValue CreditsResponse?
) : Parcelable