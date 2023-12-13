package com.example.habittracker.recyclerViewAdapter.MVVM

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface HabitsDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(habit: EntityHabits)

    @Delete
    suspend fun delete(habit: EntityHabits)

    @Query("SELECT * from habits ORDER BY time_created DESC")
    fun getAllHabits(): LiveData<List<EntityHabits>>

    @Query("SELECT COUNT(status) from habits WHERE status == 1")
    fun getCompletedHabitsCount(): LiveData<Double>

    @Query("UPDATE habits SET status = :newStatus WHERE id = :itemId")
    suspend fun updateStatusTrue(itemId: Long, newStatus: Boolean= true)

    @Query("UPDATE habits SET status = :newStatus WHERE id = :itemId")
    suspend fun updateStatusFalse(itemId: Long, newStatus: Boolean= false)
}