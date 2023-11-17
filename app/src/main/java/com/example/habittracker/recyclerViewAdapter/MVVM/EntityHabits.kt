package com.example.habittracker.recyclerViewAdapter.MVVM

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "habits")
data class EntityHabits (
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "status") val status: Boolean,
    @ColumnInfo(name = "time_created") val time_created: String
)