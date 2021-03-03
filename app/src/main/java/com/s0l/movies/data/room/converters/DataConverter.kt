package com.s0l.movies.data.room.converters

import androidx.room.TypeConverter
import com.s0l.movies.data.model.entity.*
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import java.lang.reflect.Type

class DataConverter {

    private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

    @TypeConverter
    fun listGenresToJson(value: List<GenreEntity>): String = toJson(value)

    @TypeConverter
    fun jsonToListGenres(value: String): List<GenreEntity>? = fromJson(value)

    @TypeConverter
    fun listActorsToJson(value: List<ActorEntity>): String = toJson(value)

    @TypeConverter
    fun jsonToListActors(value: String): List<ActorEntity>? = fromJson(value)

    @TypeConverter
    fun listCrewToJson(value: List<CrewEntity>): String = toJson(value)

    @TypeConverter
    fun jsonToListCrew(value: String): List<CrewEntity>? = fromJson(value)

    @TypeConverter
    fun listReviewsToJson(value: List<ReviewEntity>): String = toJson(value)

    @TypeConverter
    fun jsonToListReviews(value: String): List<ReviewEntity>? = fromJson(value)

    @TypeConverter
    fun listVideoToJson(value: List<VideoEntity>): String = toJson(value)

    @TypeConverter
    fun jsonToListVideo(value: String): List<VideoEntity>? = fromJson(value)

    @TypeConverter
    fun listSpokenLanguagesToJson(value: List<SpokenLanguagesEntity>): String = toJson(value)

    @TypeConverter
    fun jsonToListSpokenLanguages(value: String): List<SpokenLanguagesEntity>? = fromJson(value)

    private inline fun <reified T> fromJson(value: String): List<T>? {
        val type: Type = Types.newParameterizedType(List::class.java, T::class.java)
        val jsonAdapter = moshi.adapter<List<T>>(type)
        return jsonAdapter.fromJson(value)
    }

    private inline fun <reified T> toJson(value: List<T>?): String {
        val type: Type = Types.newParameterizedType(List::class.java, T::class.java)
        val jsonAdapter = moshi.adapter<List<T>>(type)
        return jsonAdapter.toJson(value)
    }

}