package com.example.habittracker

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.habittracker.databinding.FragmentSplashScreenBinding

class SplashScreenFragment : Fragment() {


    private lateinit var bindingSplashScreen : FragmentSplashScreenBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        bindingSplashScreen = FragmentSplashScreenBinding.inflate(inflater, container, false)

        return bindingSplashScreen.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val animationImage : Animation = AnimationUtils.loadAnimation(context, R.anim.image_anim)
        bindingSplashScreen.image.startAnimation(animationImage)

        val animationButton : Animation = AnimationUtils.loadAnimation(context, R.anim.button_anim)
        bindingSplashScreen.button.startAnimation(animationButton)

        val animationText : Animation = AnimationUtils.loadAnimation(context, R.anim.text_anim)
        bindingSplashScreen.text.startAnimation(animationText)

        bindingSplashScreen.button.setOnClickListener {
            findNavController().navigate(R.id.action_splashScreenFragment_to_habitsFragment)
        }

    }

}