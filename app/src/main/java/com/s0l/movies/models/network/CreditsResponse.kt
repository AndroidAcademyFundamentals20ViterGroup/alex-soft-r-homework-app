package com.s0l.movies.models.network

import com.s0l.movies.models.entity.Person


data class CreditsResponse(
  val cast: List<Person>,//Actors
  val crew: List<Person>,//Crew
)
