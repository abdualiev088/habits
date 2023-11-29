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
import com.example.habittracker.recyclerViewAdapter.MVVM.HabitViewModel
import com.google.android.material.snackbar.Snackbar

class RecyclerViewAdapter(
    private val viewModel: HabitViewModel,
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
        val doneBtn: ImageView
        val unDoneBtn : ImageView


        init {
            title = view.findViewById(R.id.title)
            status = view.findViewById(R.id.status)
            swipeRevealLayout = view.findViewById(R.id.sw_item)
            deletebtn = view.findViewById(R.id.delete)
            doneBtn = view.findViewById(R.id.done)
            unDoneBtn = view.findViewById(R.id.unDone)

            deletebtn.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION){
                    listener.onDeleteClick(data[position])

                    val snackbar = Snackbar.make(itemView, "Item deleted", Snackbar.LENGTH_LONG)
                    snackbar.setAction("Undo") {
                        viewModel.undoDelete()
                    }
                    snackbar.show()
                }
            }
            doneBtn.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION){
                    listener.onDoneClick(data[position])
                }
            }
            unDoneBtn.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION){
                    listener.onUnDoneClick(data[position])
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