package com.example.habittracker.recyclerViewAdapter.MVVM

import androidx.room.TypeConverter
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class DateConverter {
    private val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS")

    @TypeConverter
    fun fromString(value: String?): LocalDateTime? {
        return value?.let { LocalDateTime.parse(it, formatter) }
    }

    @TypeConverter
    fun dateToString(date: LocalDateTime?): String? {
        return date?.format(formatter)
    }
}
