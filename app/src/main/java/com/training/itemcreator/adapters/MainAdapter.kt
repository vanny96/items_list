package com.training.itemcreator.adapters

import android.content.Context
import android.provider.Settings.Global.getString
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.training.itemcreator.R

class MainAdapter(
    private val context: Context,
    private val data: MutableList<String>,
    private val clickListener: (v: View, position: Int) -> Unit
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
            String.format(context.getString(R.string.row_text), data.get(position))
    }

    // View Holder class
    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        val textElement: TextView = itemView.findViewById<TextView>(R.id.textView).apply {
            setOnClickListener(this@MyViewHolder)
        }

        override fun onClick(v: View?) {
            v?.let{ clickListener(it, adapterPosition)}
        }
    }

    // Utility
    fun getItem(position: Int): String {
        return data.get(position)
    }

    fun addItem(name : String){
        data.add(name)
        notifyDataSetChanged()
    }
}