package com.s0l.movies.data.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movies")
data class MovieEntity(
    @PrimaryKey(autoGenerate = true)
    val pk: Int = 0,
    val id: Int,
    val title: String,
    val overview: String?,
    val poster_path: String?,
    val backdrop_path: String?,
    val vote_count: Int,
    val vote_average: Float,
    val adult: Boolean = false,
    var runtime: Int?,
    val video: Boolean,
    val release_date: String,
    val original_title: String,
    val original_language: String,
    val popularity: Float,
    val homepage: String?,
    val imdb_id: String?,
    val status: String,//Allowed Values: Rumored, Planned, In Production, Post Production, Released, Canceled
    val tagline: String?,
    var videos: List<VideoEntity>?,
    var reviews: List<ReviewEntity>?,
    var genres: List<GenreEntity>,
    val actors: List<ActorEntity>,//Actors
    val crew: List<CrewEntity>,//Crew
    val spoken_languages: List<SpokenLanguagesEntity>,
//    val author_details: AuthorDetailsEntity?,
    )