package com.example.habittracker.recyclerViewAdapter.MVVM

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.habittracker.recyclerViewAdapter.HabitData

@Database(entities = [EntityHabits::class], version = 1, exportSchema = false)
abstract class HabitDatabase : RoomDatabase() {

    abstract fun getNotesDao(): HabitsDao

    companion object {
        // Singleton prevents multiple
        // instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: HabitDatabase? = null

        fun getDatabase(context: Context): HabitDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    HabitDatabase::class.java,
                    "habit_db"
                ).build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}