package com.training.itemcreator.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputEditText
import com.training.itemcreator.R
import com.training.itemcreator.adapters.MainAdapter
import com.training.itemcreator.repository.TodoRepository
import com.training.itemcreator.util.hideKeyboard
import com.training.itemcreator.viewmodel.TodoListViewModel
import com.training.itemcreator.viewmodel.factory.TodoViewModelFactory
import kotlinx.android.synthetic.main.fragment_main_list.*
import java.util.*

class MainList : Fragment() {

    private lateinit var todoListViewModel: TodoListViewModel

    var adapter: MainAdapter? = null
    var recyclerView: RecyclerView? = null
    var inputText: TextInputEditText? = null
    var button: Button? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_main_list, container, false)

        initRecycler(view)
        initViewModel(view)

        inputText = view?.findViewById(R.id.input_text)
        inputText?.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE) onAddItem(v) else false;
        }

        button = view?.findViewById(R.id.button2)
        button?.setOnClickListener { onAddItem(it) }

        return view;
    }

    private fun initViewModel(view: View){
        todoListViewModel = ViewModelProvider(this, TodoViewModelFactory(view.context))
            .get(TodoListViewModel::class.java)

        todoListViewModel.getTodos().observe(viewLifecycleOwner, Observer {
            adapter?.data = it
            adapter?.notifyDataSetChanged()
        })
    }


    private fun initRecycler(view: View){
        adapter = MainAdapter(view.context, Collections.emptyList()) { _, todo ->
            todo.id?.let {
                findNavController().navigate(MainListDirections.getDetail(it))
            }
        }
        recyclerView = view.findViewById<RecyclerView>(R.id.recycler)?.apply {
            adapter = this@MainList.adapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    private val onAddItem = { v: View ->
        todoListViewModel.addItem(input_text.text.toString())
        recyclerView?.smoothScrollToPosition(adapter?.getLastItem() ?: 0)
        inputText?.text?.clear()

        hideKeyboard(v)
    }
}
