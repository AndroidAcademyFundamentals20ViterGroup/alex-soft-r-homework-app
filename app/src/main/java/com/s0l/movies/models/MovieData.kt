package com.s0l.movies.models

import androidx.annotation.DrawableRes

data class MovieData(
    val id: Long,
    val ageLimit: Int,
    val isLiked: Boolean = false,
    @DrawableRes
    val coverDrawable: Int,
    @DrawableRes
    val posterDrawable: Int? = null,
    val rating: Float,
    val reviews: Int,
    val title: String,
    val length: Int = 0,
    val tags: List<String>,
    val actorInfoList: List<ActorInfo>? = null,
    val storyLine : String = ""
)