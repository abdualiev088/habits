package com.example.habittracker.recyclerViewAdapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.chauthai.swipereveallayout.SwipeRevealLayout
import com.chauthai.swipereveallayout.ViewBinderHelper
import com.example.habittracker.R
import com.example.habittracker.UI.HabitsFragment
import com.example.habittracker.recyclerViewAdapter.MVVM.EntityHabits

class RecyclerViewAdapter(
    private val listener : HabitsFragment.OnItemClickListener
):
    RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {

    private val data = ArrayList<EntityHabits>()
    private val viewBinderHelper = ViewBinderHelper()

    inner class ViewHolder(view: View):RecyclerView.ViewHolder(view){
        val title: TextView
        val status: TextView
        val swipeRevealLayout : SwipeRevealLayout
        val deletebtn: ImageView


        init {
            title = view.findViewById(R.id.title)
            status = view.findViewById(R.id.status)
            swipeRevealLayout = view.findViewById(R.id.sw_item)
            deletebtn = view.findViewById(R.id.delete)
            deletebtn.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION){
                    listener.onButtonClick(data[position])
                }
            }

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
        val item = data.get(position)
        holder.title.text = data[position].title

        holder.status.visibility = if (data[position].status)  View.VISIBLE
        else View.INVISIBLE

        viewBinderHelper.setOpenOnlyOne(true)
        viewBinderHelper.bind(holder.swipeRevealLayout, item.toString())


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
//    interface Delete {

//    }

}