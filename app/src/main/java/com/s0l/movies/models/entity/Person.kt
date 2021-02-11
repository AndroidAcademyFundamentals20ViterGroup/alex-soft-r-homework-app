package com.s0l.movies.models.entity

import android.os.Parcelable
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

//@Parcelize
@JsonClass(generateAdapter = true)
data class Person(
    val adult: Boolean,
    val gender: Int?,
    val id: Int,
    val known_for_department: String = "",
    val name: String = "",
    val original_name: String = "",
    val popularity: Float,
    val profile_path: String?,
    val credit_id: String = "",
    val cast_id: Int,
    val character: String = "",
    val order: Int,
    val department: String = "",
    val job: String = ""
) //: Parcelable