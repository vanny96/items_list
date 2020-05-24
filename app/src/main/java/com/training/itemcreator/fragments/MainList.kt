package com.training.itemcreator.fragments

import android.content.Context
import android.os.Bundle
import android.view.*
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputEditText
import com.training.itemcreator.R
import com.training.itemcreator.adapters.MainAdapter
import com.training.itemcreator.repository.TodoRepository
import com.training.itemcreator.util.hideKeyboard
import kotlinx.android.synthetic.main.fragment_main_list.*

class MainList : Fragment() {

    private val repository = TodoRepository()

    var adapter: MainAdapter? = null
    var recyclerView: RecyclerView? = null
    var inputText: TextInputEditText? = null
    var button: Button? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_main_list, container, false)

        adapter = context?.let {
            MainAdapter(it, repository.getList()) { _, id ->
                findNavController().navigate(MainListDirections.getDetail(id))
            }
        }
        recyclerView = view?.findViewById<RecyclerView>(R.id.recycler)?.apply {
            adapter = this@MainList.adapter
            layoutManager = LinearLayoutManager(context)
        }

        inputText = view?.findViewById(R.id.input_text)
        inputText?.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE) onAddItem(v) else false;
        }

        button = view?.findViewById(R.id.button2)
        button?.setOnClickListener {onAddItem(it)}

        return view;
    }

    private val onAddItem = { v: View ->
        repository.addItem(input_text.text.toString())
        adapter?.refreshContext(repository.getList())
        recyclerView?.smoothScrollToPosition(adapter?.getLastItem() ?: 0)
        inputText?.text?.clear()

        hideKeyboard(v)
    }
}
