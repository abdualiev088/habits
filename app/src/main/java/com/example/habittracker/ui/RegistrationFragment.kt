package com.example.habittracker.ui

import android.os.Bundle
import android.util.Log.d
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import com.example.habittracker.R
import com.example.habittracker.databinding.FragmentRegistrationBinding
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth


class RegistrationFragment : Fragment() {
    lateinit var binding : FragmentRegistrationBinding

    private var stateIfLoggedIn = false
    private var arePasswordsEqual: Boolean = false

    private lateinit var email : TextInputLayout
    private lateinit var password1 :TextInputLayout
    private lateinit var password2 : TextInputLayout

    private lateinit var mAuth : FirebaseAuth


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

        mAuth = FirebaseAuth.getInstance()

        email = binding.editTextMail
        password1 = binding.editTextPassword
        password2 = binding.editTextPasswordConfirm

        passwordsValidation()
        onClickLogin(binding.loginText)

        binding.registerBtn.setOnClickListener {
            if (arePasswordsEqual) {
                binding.progressBarReg.visibility = View.VISIBLE
                requestToRegister(email.editText?.text.toString(), password1.editText?.text.toString())
            }
        }


    }

    fun passwordsValidation() {

        val LATIN_STRING: String = "a b c d e f g h i j k l m n o p q r s t u v w x y z"
        password1.editText?.doOnTextChanged { text, start, before, count ->
            if (text?.length!! > 7 && text.isNotEmpty()) {
                password1.helperText = "Password is valid"
            } else {
                binding.editTextPassword.error = "Password is not valid"
            }
            if (text.toString() == password2.editText?.text.toString()) {
                arePasswordsEqual = true
            } else {
                arePasswordsEqual = false
                password2.helperText = "Passwords are not equal"
            }
        }
        password2.editText?.doOnTextChanged { text, start, before, count ->
            if (text.toString() == password1.editText?.text.toString()) {
                arePasswordsEqual = true
                password2.helperText = "Passwords are equal"
            } else {
                arePasswordsEqual = false
                password2.error = "Passwords are not equal"
            }

        }
    }
    override fun onStart() {
        super.onStart()
        val currentUser = mAuth.currentUser
        if (currentUser != null) {
            loadFragment(RatingFragment())
        }
    }

    fun requestToRegister(email: String, password: String){
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(requireActivity()) { task ->
                binding.progressBarReg.visibility = View.GONE
                if (task.isSuccessful()) {
                    // Registration successful
                    val user = mAuth.currentUser
                    Toast.makeText(
                        context, "Success.",
                        Toast.LENGTH_SHORT
                    ).show()
                    loadFragment(LoginFragment())

                } else {
                    // Registration failed
                    Toast.makeText(
                        context, "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()
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
            loadFragment(LoginFragment())
        }
    }

}