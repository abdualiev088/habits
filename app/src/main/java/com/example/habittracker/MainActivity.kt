package com.example.habittracker

import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.habittracker.ui.HabitsFragment
import com.example.habittracker.ui.RatingFragment
import com.example.habittracker.databinding.ActivityMainBinding
import com.example.habittracker.ui.RegistrationFragment
import com.google.firebase.FirebaseApp

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        FirebaseApp.initializeApp(this)
        actionBar?.setBackgroundDrawable(ColorDrawable(getColor(R.color.black)))


        binding.bottomNav.setOnItemSelectedListener {
            when(it.itemId) {
                R.id.habitsMenuItem -> {
                    loadFragment(HabitsFragment())
                    false
                }
                R.id.ratingMenuItem -> {
                    loadFragment(RatingFragment())
                    false
                }
            }
            true
        }
    }

    private fun loadFragment(fragment: Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container_view, fragment)
        transaction.commit()
    }
}