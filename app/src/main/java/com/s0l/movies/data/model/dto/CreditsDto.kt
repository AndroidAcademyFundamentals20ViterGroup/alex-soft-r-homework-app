package com.s0l.movies.data.model.dto

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CreditsDto(
  val cast: List<PersonCast>,//Actors
  val crew: List<PersonCrew>,//Crew
) {
    @JsonClass(generateAdapter = true)
    data class PersonCast(
      val adult: Boolean,
      val gender: Int?,
      val id: Int,
      val known_for_department: String = "",
      val name: String = "",
      val original_name: String = "",
      val popularity: Float,
      val profile_path: String?,
      val cast_id: Int,
      val character: String = "",
      val credit_id: String = "",
      val order: Int,
    )

    @JsonClass(generateAdapter = true)
    data class PersonCrew(
      val adult: Boolean,
      val gender: Int?,
      val id: Int,
      val known_for_department: String = "",
      val name: String = "",
      val original_name: String = "",
      val popularity: Float,
      val profile_path: String?,
      val credit_id: String = "",
      val department: String = "",
      val job: String = "",
    )
}
