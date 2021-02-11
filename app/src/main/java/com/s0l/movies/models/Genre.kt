package com.s0l.movies.models

import com.squareup.moshi.JsonClass

//@Parcelize
@JsonClass(generateAdapter = true)
data class Genre(
    val id: Int,
    val name: String
) //: Parcelable