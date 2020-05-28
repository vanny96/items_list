package com.training.itemcreator.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.training.itemcreator.R
import com.training.itemcreator.model.Todo
import com.training.itemcreator.util.enums.Priority
import java.util.*

class TodoListRecyclerAdapter(
    context: Context,
    private val clickListener: (todo: Todo) -> Unit,
    private val swipeLeftListener: (todo: Todo) -> Unit
) : RecyclerView.Adapter<TodoListRecyclerAdapter.MyViewHolder>() {

    var data: List<Todo> = Collections.emptyList()
    private val inflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = inflater.inflate(R.layout.todo_list_row_layout, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.textElement.text = String.format(data[position].name)
        holder.priorityDot.setImageResource(
            when (data[position].priority) {
                Priority.LOW -> R.drawable.priority_low_dot
                Priority.MEDIUM -> R.drawable.priority_medium_dot
                Priority.HIGH -> R.drawable.priority_high_dot
            }
        )
    }

    // Utility
    fun onItemSwipeLeft(todoPosition: Int) {
        swipeLeftListener(data[todoPosition])
    }

    // View Holder class
    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val priorityDot: ImageView = itemView.findViewById(R.id.dotImage)

        val textElement: TextView = itemView.findViewById<TextView>(R.id.textView).apply {
            setOnClickListener { clickListener(data[adapterPosition]) }
        }
    }
}