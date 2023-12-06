package com.example.habittracker.ui

import android.os.Bundle
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
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import com.example.habittracker.R
import com.example.habittracker.databinding.FragmentRatingBinding
import com.example.habittracker.recyclerViewAdapter.HabitData
import com.example.habittracker.ui.compose.RecyclerItem
import com.google.firebase.auth.FirebaseAuth


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


    private var stateIfLoggedIn = false

    private var _binding: FragmentRatingBinding? = null
    private val binding: FragmentRatingBinding
        get() = _binding!!

    private lateinit var mAuth : FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
    _binding = FragmentRatingBinding.inflate(inflater, container, false)

    binding.composeView.apply {
        setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
        setContent {
            showUi(data)
        }
    }
    return binding.root

}

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mAuth = FirebaseAuth.getInstance()
        if(mAuth.currentUser == null){
            loadFragment(LoginFragment())
        }
        onClickLogout(binding.logoutBtn)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
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
    fun showUi(data: List<HabitData>){
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            items(data) {
                RecyclerItem(data = it)
            }
            // We use a LazyColumn since the layout manager of the RecyclerView is a vertical LinearLayoutManager
        }
    }

    @Preview
    @Composable
    fun ui(
    ){
        showUi(data)
    }

}