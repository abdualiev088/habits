package com.example.habittracker.ui

import android.graphics.Color
import android.os.Bundle
import android.util.Log.d
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ekn.gruzer.gaugelibrary.Range
import com.example.habittracker.R
import com.example.habittracker.databinding.FragmentHabitsBinding
import com.example.habittracker.firebase.UserDataset
import com.example.habittracker.recyclerViewAdapter.MVVM.EntityHabits
import com.example.habittracker.recyclerViewAdapter.MVVM.HabitViewModel
import com.example.habittracker.recyclerViewAdapter.RecyclerViewAdapter
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.childEvents
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class HabitsFragment : Fragment() {

    private lateinit var binding : FragmentHabitsBinding

    private lateinit var viewModel: HabitViewModel
    private lateinit var habitsRecyclerView: RecyclerView

    private lateinit var mAuth: FirebaseAuth
    private lateinit var databaseReference : DatabaseReference


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHabitsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mAuth = FirebaseAuth.getInstance()
        databaseReference = FirebaseDatabase.getInstance().getReference("Users")
        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
        ).get(HabitViewModel::class.java)

        viewModel.allHabits.observe(viewLifecycleOwner, Observer { list ->
            list?.let {
                if (list.isNotEmpty()){
                    binding.haveHabitsUi.visibility = View.VISIBLE
                    binding.noHabitsUi.visibility = View.INVISIBLE
                }
                else {
                    binding.haveHabitsUi.visibility = View.INVISIBLE
                    binding.noHabitsUi.visibility = View.VISIBLE
                }
            }
        })

        val listener = object : OnItemClickListener{
            override fun onDeleteClick(habit: EntityHabits) {
                viewModel.deleteHabit(habit)
            }

            override fun onDoneClick(habit: EntityHabits) {
                viewModel.updateStatusTrue(habit.id)
            }

            override fun onUnDoneClick(habit: EntityHabits) {
                viewModel.updateStatusFalse(habit.id)
            }
        }

        habitsRecyclerView = binding.rc
        val habitAdapter = RecyclerViewAdapter(listener = listener, viewModel = viewModel)
        val rc_manager = LinearLayoutManager(context)
        habitsRecyclerView.adapter = habitAdapter



        rc_manager.orientation = LinearLayoutManager.VERTICAL
        habitsRecyclerView.layoutManager = rc_manager

        binding.rc.adapter
//        55 000

        viewModel.countHabits.observe(viewLifecycleOwner, Observer { count ->
            count?.let {
                d("HabitsFragment", "countHabits updated: $count")
                val range = Range()
                range.color = Color.parseColor("#000000")
                range.from = 0.0
                range.to = 50.0

                val halfGauge = binding.arcGauge

                halfGauge.addRange(range)
                halfGauge.minValue = 0.0
                halfGauge.maxValue = count
                viewModel.allCompletedHabitsCount.observe(viewLifecycleOwner, Observer {countTrue ->
                    countTrue?.let {
                        halfGauge.value = countTrue
                        val userUid = mAuth.currentUser?.uid
                        val email = mAuth.currentUser?.email.toString()
                        val task_percent = (countTrue.div(count).times(100))
                        if (userUid != null){
                            val userHabits = UserDataset(
                                task_percent,
                                email
                            )
                            databaseReference.child(userUid).setValue(userHabits)
                        }
                    }
                })
            }
        })

        viewModel.allHabits.observe(viewLifecycleOwner, Observer { list ->
            list?.let {
                habitAdapter.updateList(it)
//                This is adaptive setting value to dashboard
                viewModel.updateCountHabits(list.size.toDouble())
            }
        })


        binding.createTask.setOnClickListener {
            showBottomSheetDialog()
        }

    }
    private fun showBottomSheetDialog(){
        val bottomSheetDialog = BottomSheetDialog(requireContext())
        bottomSheetDialog.setContentView(R.layout.bottomsheet_habits)
        bottomSheetDialog.show()

        val editText = bottomSheetDialog.findViewById<EditText>(R.id.habitTitle)
        val button = bottomSheetDialog.findViewById<Button>(R.id.createHabitBtn)
        button?.setOnClickListener {
            if (editText?.text.toString().isNotEmpty()){
                val customDate = LocalDateTime.parse(
                    LocalDateTime.now().toString(),
                    DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS"))

                val newHabit = EntityHabits(
                    title = editText!!.text.toString(),
                    status = false,
                    time_created = customDate)
                viewModel.addHabit(newHabit)
                Toast.makeText(context, "${editText.text.toString()} Added", Toast.LENGTH_LONG).show()
                bottomSheetDialog.hide()
            }
        }
    }

    interface OnItemClickListener {
        fun onDeleteClick(habit: EntityHabits)

        fun onDoneClick(habit: EntityHabits)

        fun onUnDoneClick(habit: EntityHabits)
    }

    private fun loadFragment(fragment: Fragment){
        val transaction = activity?.supportFragmentManager?.beginTransaction()!!
        transaction.replace(R.id.fragment_container_view, fragment)
        transaction.commit()
    }

}

