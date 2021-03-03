package com.s0l.movies.data.room.converters

import androidx.room.TypeConverter
import com.squareup.moshi.Moshi

open class IntegerListConverter {
    private val moshi = Moshi.Builder().build()
    @TypeConverter
    fun fromString(value: String): List<Int> {
        return value.split(",").map { it.toInt() }
    }

    @TypeConverter
    fun fromList(list: List<Int>): String {
        return list.joinToString(",")
    }
}