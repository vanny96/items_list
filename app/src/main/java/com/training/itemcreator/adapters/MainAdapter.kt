package com.training.itemcreator.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.training.itemcreator.R
import com.training.itemcreator.model.Todo

class MainAdapter(
    context: Context,
    var data: List<Todo>,
    private val clickListener: (v: View, todo: Todo) -> Unit
) : RecyclerView.Adapter<MainAdapter.MyViewHolder>() {

    private val inflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = inflater.inflate(R.layout.row_layout, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.textElement.text =
            String.format(data[position].name ?: "Generic")
    }

    // View Holder class
    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        val textElement: TextView = itemView.findViewById<TextView>(R.id.textView).apply {
            setOnClickListener(this@MyViewHolder)
        }

        override fun onClick(v: View?) {
            v?.let{ clickListener(it, data[adapterPosition])}
        }
    }

    // Utility
    fun getLastItem() : Int{
        return if(data.isEmpty()) 0 else data.size - 1
    }
}