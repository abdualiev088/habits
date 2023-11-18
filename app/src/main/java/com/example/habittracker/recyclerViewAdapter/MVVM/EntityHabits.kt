package com.example.habittracker.recyclerViewAdapter.MVVM

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import java.time.LocalDateTime
import java.util.Date

@Entity(tableName = "habits")
data class EntityHabits (
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "status") val status: Boolean,
    @TypeConverters(DateConverter::class)
    @ColumnInfo(name = "time_created") val time_created: LocalDateTime
)