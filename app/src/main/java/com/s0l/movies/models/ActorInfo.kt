package com.s0l.movies.models

import androidx.annotation.DrawableRes

data class ActorInfo(
    val id: Int,
    val name: String,
    @DrawableRes
    val image: Int
)