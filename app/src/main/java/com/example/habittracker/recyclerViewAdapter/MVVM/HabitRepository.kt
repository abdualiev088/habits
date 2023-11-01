package com.example.habittracker.recyclerViewAdapter.MVVM

import com.example.habittracker.recyclerViewAdapter.HabitData

class HabitRepository {
    fun getHabits(): Array<HabitData>{
        return arrayOf(
            HabitData("One", true, "2001-11-23"),
            HabitData("One", false, "2001-11-23")
        )
    }
}