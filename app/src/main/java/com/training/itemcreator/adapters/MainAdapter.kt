package com.training.itemcreator.adapters

import android.content.Context
import android.view.*
import android.widget.TextView
import androidx.core.view.GestureDetectorCompat
import androidx.recyclerview.widget.RecyclerView
import com.training.itemcreator.R
import com.training.itemcreator.gestures.TodoItemGestureDetector
import com.training.itemcreator.model.Todo

class MainAdapter(
    context: Context,
    var data: List<Todo>,
    private val clickListener: (todo: Todo) -> Unit,
    private val swipeLeftListener: (todo: Todo) -> Unit
) : RecyclerView.Adapter<MainAdapter.MyViewHolder>() {

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

    // View Holder class
    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val gestureDetector = GestureDetectorCompat(itemView.context, TodoItemGestureDetector(
            { clickListener(data[adapterPosition]) },
            { swipeLeftListener(data[adapterPosition]) }
        ))

        val textElement: TextView = itemView.findViewById<TextView>(R.id.textView).apply {
            setOnTouchListener(View.OnTouchListener { _, event ->
                gestureDetector.onTouchEvent(event)
                return@OnTouchListener true
            })
        }
    }

}