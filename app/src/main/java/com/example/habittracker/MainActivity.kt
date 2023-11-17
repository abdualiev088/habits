package com.example.habittracker

import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import com.example.habittracker.databinding.ActivityMainBinding
import com.example.habittracker.databinding.FragmentSplashScreenBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        actionBar?.setBackgroundDrawable(ColorDrawable(getColor(R.color.black)))
    }
}