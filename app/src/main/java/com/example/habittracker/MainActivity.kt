package com.example.habittracker

import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import com.example.habittracker.databinding.ActivityMainBinding
import com.example.habittracker.databinding.FragmentSplashScreenBinding
import com.google.firebase.FirebaseApp

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        FirebaseApp.initializeApp(this)
        actionBar?.setBackgroundDrawable(ColorDrawable(getColor(R.color.black)))
    }
}