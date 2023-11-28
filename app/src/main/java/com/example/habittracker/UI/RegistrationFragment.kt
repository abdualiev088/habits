package com.example.habittracker.UI

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.habittracker.R
import com.example.habittracker.databinding.FragmentHabitsBinding
import com.example.habittracker.databinding.FragmentRatingBinding
import com.example.habittracker.databinding.FragmentRegistrationBinding

class RegistrationFragment : Fragment() {

    private var stateIfLoggedIn = false

    lateinit var binding : FragmentRegistrationBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentRegistrationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

//    private fun checkOutState(stateOfUser: Boolean){
//        when(stateOfUser){
//
//        }
//    }


}