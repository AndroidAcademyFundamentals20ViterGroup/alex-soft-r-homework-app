package com.s0l.movies.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Parcelize
data class Movie(
    val id: Int,
    val title: String,
    val overview: String, //storyLine
    val poster: String, //posterDrawable
    val backdrop: String, //coverDrawable
    val ratings: Float, //rating
    val numberOfRatings: Int, //reviews
    val minimumAge: Int, //ageLimit
    val runtime: Int, //length
    val genres: @RawValue List<Genre>, //tags
    val actors: @RawValue List<Actor> //actorInfoList
) : Parcelable