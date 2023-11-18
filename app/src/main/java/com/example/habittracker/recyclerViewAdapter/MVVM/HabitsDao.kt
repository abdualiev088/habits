package com.example.habittracker.recyclerViewAdapter.MVVM

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.habittracker.recyclerViewAdapter.HabitData

@Dao
interface HabitsDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(habit: EntityHabits)

    @Delete
    suspend fun delete(habit: EntityHabits)

    @Query("SELECT * from habits ORDER BY time_created DESC")
    fun getAllHabits(): LiveData<List<EntityHabits>>
}