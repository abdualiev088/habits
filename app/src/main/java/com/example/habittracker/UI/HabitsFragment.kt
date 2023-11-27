package com.example.habittracker.UI

import android.graphics.Color
import android.os.Bundle
import android.util.Log.d
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.get
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ekn.gruzer.gaugelibrary.Range
import com.example.habittracker.R
import com.example.habittracker.databinding.FragmentHabitsBinding
import com.example.habittracker.recyclerViewAdapter.MVVM.EntityHabits
import com.example.habittracker.recyclerViewAdapter.MVVM.HabitViewModel
import com.example.habittracker.recyclerViewAdapter.RecyclerViewAdapter
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.FirebaseApp
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.properties.Delegates


class HabitsFragment : Fragment() {

    private lateinit var binding : FragmentHabitsBinding

    private lateinit var viewModel: HabitViewModel
    private lateinit var habitsRecyclerView: RecyclerView

    private var countHabits by Delegates.notNull<Double>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHabitsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mAuth = FirebaseApp.getInstance()

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

        habitsRecyclerView = binding.rc
        val habitAdapter = RecyclerViewAdapter()
        val rc_manager = LinearLayoutManager(context)
        habitsRecyclerView.adapter = habitAdapter

//        swipeLayout.showMode = SwipeLayout.ShowMode.PullOut
//        swipeLayout.addDrag(SwipeLayout.DragEdge.Left, swipeLayout.findViewById(R.id.delete))
//        swipeLayout.addDrag(SwipeLayout.DragEdge.Right, swipeLayout.findViewById(R.id.done))


        rc_manager.orientation = LinearLayoutManager.VERTICAL
        habitsRecyclerView.layoutManager = rc_manager



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
//        habitList()
//        observeData(halfGauge)
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



    private fun ifNoData(){

    }
}

