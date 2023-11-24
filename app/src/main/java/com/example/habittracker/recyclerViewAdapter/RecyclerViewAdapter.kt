package com.example.habittracker.recyclerViewAdapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.chauthai.swipereveallayout.SwipeRevealLayout
import com.chauthai.swipereveallayout.ViewBinderHelper
import com.example.habittracker.R
import com.example.habittracker.recyclerViewAdapter.MVVM.EntityHabits

class RecyclerViewAdapter(


):
    RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {

    private val data = ArrayList<EntityHabits>()
    private val viewBinderHelper = ViewBinderHelper()

    inner class ViewHolder(view: View):RecyclerView.ViewHolder(view){
        val swipeLayout: SwipeRevealLayout
        val rightButtonsLayout: LinearLayout = itemView.findViewById(R.id.rightButtonsLayout)
        val completeButton: ImageView = itemView.findViewById(R.id.completeButton)
        val otherButton: ImageView = itemView.findViewById(R.id.otherButton)

        val title: TextView
        val status: TextView

        init {
            // Enable swipe to both directions
            viewBinderHelper.setOpenOnlyOne(true)

            swipeLayout = view.findViewById(R.id.swipeLayout)
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
        val item = data[position]
        holder.title.text = item.title

        holder.status.visibility = if (item.status)  View.VISIBLE
        else View.INVISIBLE

        viewBinderHelper.bind(holder.swipeLayout, item.title)

        holder.completeButton.setOnClickListener {

        }

    }
    fun updateList(newList: List<EntityHabits>) {
        // on below line we are clearing
        // our notes array list
        data.clear()
        // on below line we are adding a
        // new list to our all notes list.
        data.addAll(newList)
        // on below line we are calling notify data
        // change method to notify our adapter.
        notifyDataSetChanged()
    }


}