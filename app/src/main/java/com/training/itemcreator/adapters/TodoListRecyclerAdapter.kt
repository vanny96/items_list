package com.training.itemcreator.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.training.itemcreator.R
import com.training.itemcreator.model.Todo

class TodoListRecyclerAdapter(
    context: Context,
    var data: List<Todo>,
    private val clickListener: (todo: Todo) -> Unit,
    private val swipeLeftListener: (todo: Todo) -> Unit
) : RecyclerView.Adapter<TodoListRecyclerAdapter.MyViewHolder>() {

    private val inflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = inflater.inflate(R.layout.todo_list_row_layout, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.textElement.text =
            String.format(data[position].name ?: "Generic")
    }

    // Utility
    fun getLastItem(): Int {
        return if (data.isEmpty()) 0 else data.size - 1
    }

    fun onItemSwipeLeft(todoPosition: Int){
        swipeLeftListener(data[todoPosition])
    }

    // View Holder class
    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val textElement: TextView = itemView.findViewById<TextView>(R.id.textView).apply {
            setOnClickListener { clickListener(data[adapterPosition]) }
        }
    }

}