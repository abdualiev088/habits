package com.example.habittracker.recyclerViewAdapter.MVVM

import androidx.lifecycle.LiveData
import com.example.habittracker.recyclerViewAdapter.HabitData
import java.sql.Timestamp
import java.util.Date

class HabitRepository(private var habitsDao: HabitsDao) {

    val allHabits : LiveData<List<EntityHabits>> = habitsDao.getAllHabits()
    val getCompletedHabitsCount: LiveData<Double> = habitsDao.getCompletedHabitsCount()

    suspend fun insert(habit: EntityHabits){
        habitsDao.insert(habit)
    }

    suspend fun delete(habit: EntityHabits){
        habitsDao.delete(habit)
    }

    suspend fun updateStatusTrue(itemId: Long){
        habitsDao.updateStatusTrue(itemId)
    }

    suspend fun updateStatusFalse(itemId: Long){
        habitsDao.updateStatusFalse(itemId)
    }


}