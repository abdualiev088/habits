package com.example.habittracker.recyclerViewAdapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.habittracker.R

class RecyclerViewAdapter(private val data: Array<HabitData>):
    RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {

    inner class ViewHolder(view: View):RecyclerView.ViewHolder(view){
        val title: TextView
        val status: TextView

        init {
            title = view.findViewById(R.id.title)
            status = view.findViewById(R.id.status)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.rc_item, parent, false)
        return ViewHolder(view.rootView)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.title.text = data[position].title

        holder.status.visibility = if (data[position].status)  View.VISIBLE
        else View.INVISIBLE

    }


}