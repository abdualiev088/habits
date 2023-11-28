package com.example.habittracker.recyclerViewAdapter.MVVM

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.habittracker.recyclerViewAdapter.HabitData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HabitViewModel(application: Application): AndroidViewModel(application) {

    val allHabits : LiveData<List<EntityHabits>>
    private val _countHabits = MutableLiveData<Double>()
    val countHabits: LiveData<Double> get() = _countHabits

    val allCompletedHabitsCount: LiveData<Double>

    val repository: HabitRepository

//    private val _habitList = MutableLiveData<List<EntityHabits>>()
//    val habitList: LiveData<List<EntityHabits>> get() = _habitList

    init {
        val dao = HabitDatabase.getDatabase(application).getNotesDao()
        repository = HabitRepository(dao)
        allHabits = repository.allHabits
        allCompletedHabitsCount = repository.getCompletedHabitsCount
    }

    fun deleteHabit(habitData: EntityHabits) = viewModelScope.launch(Dispatchers.IO){
        repository.delete(habitData)
    }

    fun addHabit(habitData: EntityHabits) = viewModelScope.launch(Dispatchers.IO){
        repository.insert(habitData)
    }

    fun updateCountHabits(value: Double) {
        _countHabits.value = value
    }

    fun updateStatus(userId: EntityHabits) {
        viewModelScope.launch {
            repository.update(userId)
            // Optionally, fetch updated data and update the LiveData
        }
    }



}