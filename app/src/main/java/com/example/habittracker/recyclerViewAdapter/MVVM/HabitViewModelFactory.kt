package com.example.habittracker.recyclerViewAdapter.MVVM

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

class HabitViewModelFactory(private var application: Application) : ViewModelProvider.AndroidViewModelFactory(application) {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HabitViewModel::class.java))
            return HabitViewModel(application) as T
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}