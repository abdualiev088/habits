package com.example.habittracker.recyclerViewAdapter.MVVM

import androidx.lifecycle.LiveData
import com.example.habittracker.recyclerViewAdapter.HabitData
import java.sql.Timestamp
import java.util.Date

class HabitRepository(private var habitsDao: HabitsDao) {

    val allHabits : LiveData<List<EntityHabits>> = habitsDao.getAllHabits()

    suspend fun insert(habit: EntityHabits){
        habitsDao.insert(habit)
    }

    suspend fun delete(habit: EntityHabits){
        habitsDao.delete(habit)
    }


}