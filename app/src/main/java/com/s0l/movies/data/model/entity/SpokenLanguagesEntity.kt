package com.s0l.movies.data.model.entity

import androidx.room.PrimaryKey

data class SpokenLanguagesEntity(
    @PrimaryKey(autoGenerate = true)
    val pk: Int = 0,
    val english_name: String,
    val iso_639_1: String,
    val name: String
)