package com.example.habittracker

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ekn.gruzer.gaugelibrary.Range
import com.example.habittracker.databinding.FragmentHabitsBinding
import com.example.habittracker.recyclerViewAdapter.MVVM.EntityHabits
import com.example.habittracker.recyclerViewAdapter.MVVM.HabitViewModel
import com.example.habittracker.recyclerViewAdapter.RecyclerViewAdapter
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Date


class HabitsFragment : Fragment() {

    private lateinit var binding : FragmentHabitsBinding

    private lateinit var viewModel: HabitViewModel
    private lateinit var habitsRecyclerView: RecyclerView
    private lateinit var buttonFAB: FloatingActionButton

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHabitsBinding.inflate(inflater, container, false)
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
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        habitsRecyclerView = binding.rc
        val habitAdapter = RecyclerViewAdapter()
        val rc_manager = LinearLayoutManager(context)
        habitsRecyclerView.adapter = habitAdapter

        rc_manager.orientation = LinearLayoutManager.VERTICAL
        habitsRecyclerView.layoutManager = rc_manager


        viewModel.allHabits.observe(viewLifecycleOwner, Observer { list ->
            list?.let {
                habitAdapter.updateList(it)
            }
        })


        val range = Range()
        range.color = Color.parseColor("#ce0000")
        range.from = 0.0
        range.to = 50.0

        val halfGauge = binding.arcGauge

        halfGauge.addRange(range)
        halfGauge.minValue = 0.0
        halfGauge.maxValue = 100.0
        halfGauge.value = 10.0

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

//                val sdf = SimpleDateFormat("dd MMM, yyyy - HH:mm")
//                val currentDateAndTime: String = sdf.format(Date())

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


//    override fun onCreateContextMenu(
//        menu: ContextMenu,
//        v: View,
//        menuInfo: ContextMenu.ContextMenuInfo?
//    ) {
//        super.onCreateContextMenu(menu, v, menuInfo)
//        val inflater = MenuInflater(v.context)
//        inflater.inflate(R.menu.swipe_buttons, menu)
//    }

//    private fun attachToRv(rv: RecyclerView){
//
//        val swipeController = SwipeCallback()
//        val touchHelper = ItemTouchHelper(swipeController)
//        touchHelper.attachToRecyclerView(rv)
//
//    }

    private fun ifNoData(){

    }
}

