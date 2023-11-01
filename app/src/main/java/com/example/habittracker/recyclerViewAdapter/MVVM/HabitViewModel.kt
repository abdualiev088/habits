package com.example.habittracker.recyclerViewAdapter.MVVM

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.habittracker.recyclerViewAdapter.HabitData

class HabitViewModel: ViewModel() {
    private val habitRepository = HabitRepository()

    private val _habits = MutableLiveData<Array<HabitData>>()
    val habits : LiveData<Array<HabitData>> = _habits

    fun loadHabit(){
        val habit = habitRepository.getHabits()
        _habits.value = habit
    }
}