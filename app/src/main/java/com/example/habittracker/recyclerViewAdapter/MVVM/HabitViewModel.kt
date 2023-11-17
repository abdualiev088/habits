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
    val repository: HabitRepository

    init {
        val dao = HabitDatabase.getDatabase(application).getNotesDao()
        repository = HabitRepository(dao)
        allHabits = repository.allHabits
    }

    fun deleteHabit(habitData: EntityHabits) = viewModelScope.launch(Dispatchers.IO){
        repository.delete(habitData)
    }

    fun addHabit(habitData: EntityHabits) = viewModelScope.launch(Dispatchers.IO){
        repository.insert(habitData)
    }


}