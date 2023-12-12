package com.example.habittracker.ui

import android.R.attr
import android.os.Bundle
import android.util.Log
import android.util.Log.d
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import com.example.habittracker.R
import com.example.habittracker.databinding.FragmentLoginBinding
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth


class LoginFragment : Fragment() {

    private lateinit var email : TextInputLayout
    private lateinit var password1 : TextInputLayout
    private lateinit var mAuth: FirebaseAuth

    var areFieldsFill = false

    lateinit var binding : FragmentLoginBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mAuth = FirebaseAuth.getInstance()
        email = binding.editTextMailLogin
        password1 = binding.editTextPasswordLogin
        fieldsValidation()
        onClickLogin(binding.registerText)
        binding.loginBtn.setOnClickListener {
            if (areFieldsFill) {
                binding.progressBarLog.visibility = View.VISIBLE
                requestToLogin(email.editText?.text.toString(), password1.editText?.text.toString())
            }
            else{
                Toast.makeText(
                    context, "Fill all inputs correctly.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    fun requestToLogin(email: String, password: String){
//        mAuth = FirebaseAuth.getInstance()

        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(requireActivity()) { task ->
                binding.progressBarLog.visibility = View.GONE
                if (task.isSuccessful()) {
                    // Sign in success
                    val user = mAuth.currentUser
                    d("userUid", user?.uid.toString())
                    loadFragment(RatingFragment())
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(
                        context, "Login failed.",
                        Toast.LENGTH_SHORT
                    ).show()
                    d("loginE", task.exception.toString())
                }
            }
    }
    private fun fieldsValidation() {
        password1.editText?.doOnTextChanged { text, start, before, count ->
            if (text?.length!! > 7 && text.isNotEmpty()) {
                password1.helperText = "Password is valid"
                areFieldsFill = true
            } else {
                binding.editTextPasswordLogin.error = "Password is not valid"
                areFieldsFill = false
            }
        }
    }


    private fun loadFragment(fragment: Fragment){
        val transaction = activity?.supportFragmentManager?.beginTransaction()!!
        transaction.replace(R.id.fragment_container_view, fragment)
        transaction.commit()
    }
    private fun onClickLogin(btn: TextView){
        btn.setOnClickListener {
            loadFragment(RegistrationFragment())
        }
    }

    override fun onStart() {
        super.onStart()
        val currentUser = mAuth.currentUser
        if (currentUser != null) {
            loadFragment(RatingFragment())
        }
    }
}