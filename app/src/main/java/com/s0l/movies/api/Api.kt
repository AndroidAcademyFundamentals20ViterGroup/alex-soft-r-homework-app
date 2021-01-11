package com.s0l.movies.api

object Api {
  private const val BASE_POSTER_PATH = "https://image.tmdb.org/t/p/w342"
  private const val BASE_BACKDROP_PATH = "https://image.tmdb.org/t/p/w780"
  private const val PROFILE_BACKDROP_PATH = "https://image.tmdb.org/t/p/w185/"

  fun getPosterPath(posterPath: String): String {
    return BASE_POSTER_PATH + posterPath
  }

  fun getBackdropPath(backdropPath: String): String {
    return BASE_BACKDROP_PATH + backdropPath
  }

  fun getProfilePath(profilePath: String): String {
    return PROFILE_BACKDROP_PATH + profilePath
  }

}
