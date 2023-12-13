package com.example.habittracker.ui

import android.os.Bundle
import android.util.Log.d
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import com.airbnb.lottie.utils.Utils
import com.example.habittracker.R
import com.example.habittracker.databinding.FragmentRatingBinding
import com.example.habittracker.firebase.UserDataset
import com.example.habittracker.recyclerViewAdapter.HabitData
import com.example.habittracker.ui.compose.RecyclerItem
import com.google.android.material.resources.TypefaceUtils
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseException
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class RatingFragment : Fragment() {

    val data = listOf(
        HabitData("onehiuhuirhtgiouhs", false, "10.02.2001"),
        HabitData("one", false, "10.02.2001"),
        HabitData("one", false, "10.02.2001"),
        HabitData("one", false, "10.02.2001"),
        HabitData("one", false, "10.02.2001"),
        HabitData("one", false, "10.02.2001"),
        HabitData("one", false, "10.02.2001"),
        HabitData("one", false, "10.02.2001"),
        HabitData("one", false, "10.02.2001"),
        HabitData("one", false, "10.02.2001"),
        HabitData("one", false, "10.02.2001"),
        HabitData("one", false, "10.02.2001"),
        HabitData("one", false, "10.02.2001"),
        HabitData("one", false, "10.02.2001")
    )

    private var _binding: FragmentRatingBinding? = null
    private val binding: FragmentRatingBinding
        get() = _binding!!

    private lateinit var mAuth : FirebaseAuth
    private lateinit var db : DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
    _binding = FragmentRatingBinding.inflate(inflater, container, false)

    binding.composeView.apply {
        setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
        setContent {
            UserList()
        }
    }
    return binding.root

}

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mAuth = FirebaseAuth.getInstance()
        db = FirebaseDatabase.getInstance().getReference( "Users")
//        val userUid = mAuth.uid

        if(mAuth.currentUser == null){
            loadFragment(LoginFragment())
        }
        onClickLogout(binding.logoutBtn)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onStart() {
        super.onStart()
        val currentUser = mAuth.currentUser
        if (currentUser == null) {
            loadFragment(LoginFragment())
        }
    }

    private fun onClickLogout(img: ImageView){
        img
            .setOnClickListener {
                mAuth.signOut()
                loadFragment(LoginFragment())
            }
    }


    private fun loadFragment(fragment: Fragment){
        val transaction = activity?.supportFragmentManager?.beginTransaction()!!
        transaction.replace(R.id.fragment_container_view, fragment)
        transaction.commit()
    }

    @Composable
    fun UserList(){


        var userList by remember { mutableStateOf(emptyList<UserDataset>()) }
        var sortedUserList by remember { mutableStateOf(emptyList<UserDataset>()) }

        val userId = FirebaseAuth.getInstance().currentUser?.uid

        if (userId != null) {
            // Get a reference to the "users" node
            val usersReference = FirebaseDatabase.getInstance().getReference("Users")

            // Observe changes in the data
            DisposableEffect(Unit) {
                val valueEventListener = object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        try {
                            if(snapshot != null){
                                val updatedList = mutableListOf<UserDataset>()
                                for (userSnapshot in snapshot.children) {
                                    // Convert each user's data to a UserDataset object
                                    val user = userSnapshot.getValue(UserDataset::class.java)
                                    if (user != null) {
                                        // Add the user to the updated list
                                        updatedList.add(user)
                                    }
                                }
//                                val users = snapshot.getValue(UserDataset::class.java)
//                                d("firebase", ": " + users.toString())
                                userList = updatedList.toList()
                                sortedUserList = userList.sortedBy { it.roundedNumber ?: 0.0 }
                                d("firebase", "sortedUserList " + sortedUserList.toString())

                            }
                        } catch (e: DatabaseException) {
                            d("firebase", "E: " + e.toString())
                        // Handle the exception or log it
                        }
//
                    }

                    override fun onCancelled(error: DatabaseError) {
                        // Handle the error
                    }
                }

                usersReference.addValueEventListener(valueEventListener)

                onDispose {
                    // Remove the listener when the composable is disposed
                    usersReference.removeEventListener(valueEventListener)
                }
            }
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {

            items(sortedUserList) {
                RecyclerItem(index = sortedUserList.indexOf(it), data = it)
            }
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun UserListPreview() {
        UserList()
    }

}
