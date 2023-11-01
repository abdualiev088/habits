package com.example.habittracker

import android.graphics.Color
import android.os.Bundle
import android.view.ContextMenu
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.BackEventCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.ekn.gruzer.gaugelibrary.ArcGauge
import com.ekn.gruzer.gaugelibrary.Range
import com.example.habittracker.databinding.FragmentHabitsBinding
import com.example.habittracker.recyclerViewAdapter.HabitData
import com.example.habittracker.recyclerViewAdapter.MVVM.HabitViewModel
import com.example.habittracker.recyclerViewAdapter.RecyclerViewAdapter
import com.example.habittracker.recyclerViewAdapter.SwipeCallback
import com.google.android.material.behavior.SwipeDismissBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog


class HabitsFragment : Fragment() {

    private lateinit var binding : FragmentHabitsBinding

    private lateinit var viewModel: HabitViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHabitsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(HabitViewModel::class.java)

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


        observeData(halfGauge)
        loadData()
    }
    private fun showBottomSheetDialog(){
        val bottomSheetDialog = BottomSheetDialog(requireContext())
        bottomSheetDialog.setContentView(R.layout.bottomsheet_habits)
        bottomSheetDialog.show()
    }

    private fun observeData(halfGauge:ArcGauge){
        viewModel.habits.observe(viewLifecycleOwner, Observer { habits ->
            val habitAdapter = RecyclerViewAdapter(habits)
            binding.rc.adapter = habitAdapter
            val rc_manager = LinearLayoutManager(context)
            rc_manager.orientation = LinearLayoutManager.VERTICAL
            binding.rc.layoutManager = rc_manager

            halfGauge.value = habits.size.toDouble()
            halfGauge.maxValue = habits.size.toDouble()


        })
    }
    private fun loadData(){
        viewModel.loadHabit()
    }


    override fun onCreateContextMenu(
        menu: ContextMenu,
        v: View,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        val inflater = MenuInflater(v.context)
        inflater.inflate(R.menu.swipe_buttons, menu)
    }

    private fun attachToRv(rv: RecyclerView){

        val swipeController = SwipeCallback()
        val touchHelper = ItemTouchHelper(swipeController)
        touchHelper.attachToRecyclerView(rv)

    }

}

