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

    private val deletedItems = mutableListOf<EntityHabits>()

    init {
        val dao = HabitDatabase.getDatabase(application).getNotesDao()
        repository = HabitRepository(dao)
        allHabits = repository.allHabits
        allCompletedHabitsCount = repository.getCompletedHabitsCount
    }

    fun deleteHabit(habitData: EntityHabits) = viewModelScope.launch(Dispatchers.IO){
        repository.delete(habitData)
        deletedItems.add(habitData)
    }

    fun undoDelete() {
        viewModelScope.launch {
            if (deletedItems.isNotEmpty()) {
                val lastDeletedItem = deletedItems.removeAt(deletedItems.size - 1)
                repository.insert(lastDeletedItem)
            }
        }

    }


    fun addHabit(habitData: EntityHabits) = viewModelScope.launch(Dispatchers.IO){
        repository.insert(habitData)
    }

    fun updateCountHabits(value: Double) {
        _countHabits.value = value
    }

    fun updateStatusTrue(itemId: Long) {
        viewModelScope.launch {
            repository.updateStatusTrue(itemId)
        }
    }

    fun updateStatusFalse(itemId: Long) {
        viewModelScope.launch {
            repository.updateStatusFalse(itemId)
        }
    }



}