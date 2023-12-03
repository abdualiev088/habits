package com.example.habittracker.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.example.habittracker.MainActivity
import com.example.habittracker.R
import com.example.habittracker.databinding.ActivitySplashScreenBinding

class SplashScreenActivity : AppCompatActivity() {

    private lateinit var binding : ActivitySplashScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val animationImage : Animation = AnimationUtils.loadAnimation(applicationContext, R.anim.image_anim)
        binding.image.startAnimation(animationImage)

        val animationButton : Animation = AnimationUtils.loadAnimation(applicationContext, R.anim.button_anim)
        binding.button.startAnimation(animationButton)

        val animationText : Animation = AnimationUtils.loadAnimation(applicationContext, R.anim.text_anim)
        binding.text.startAnimation(animationText)

        binding.button.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}