package com.s0l.movies.data.room.converters

import androidx.room.TypeConverter

open class StringListConverter {

    @TypeConverter
    fun fromString(value: String): List<String> {
        return value.split(",")
    }

    @TypeConverter
    fun fromList(list: List<String>): String {
        return list.joinToString(",")
    }
}
