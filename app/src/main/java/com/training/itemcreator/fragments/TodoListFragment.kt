package com.training.itemcreator.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputEditText
import com.training.itemcreator.R
import com.training.itemcreator.adapters.MainAdapter
import com.training.itemcreator.model.Todo
import com.training.itemcreator.util.hideKeyboard
import com.training.itemcreator.viewmodel.TodoListViewModel
import com.training.itemcreator.viewmodel.factory.TodoViewModelFactory
import kotlinx.android.synthetic.main.todo_list_fragment.*
import java.util.*

class TodoListFragment : Fragment() {

    private lateinit var todoListViewModel: TodoListViewModel

    private var adapter: MainAdapter? = null
    private var recyclerView: RecyclerView? = null

    private var inputText: TextInputEditText? = null
    private var button: Button? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.todo_list_fragment, container, false)

        initRecycler(view)
        initViewModel(view)

        inputText = view.findViewById(R.id.input_text)
        inputText?.setOnEditorActionListener { v, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) onAddItem(v) else false;
        }

        button = view.findViewById(R.id.button2)
        button?.setOnClickListener { onAddItem(it) }

        return view;
    }

    private fun initViewModel(view: View) {
        todoListViewModel = ViewModelProvider(this, TodoViewModelFactory(view.context))
            .get(TodoListViewModel::class.java)

        todoListViewModel.getTodos().observe(viewLifecycleOwner, Observer {
            adapter?.data = it
            adapter?.notifyDataSetChanged()
        })

        todoListViewModel.todoAdded.observe(viewLifecycleOwner, Observer {
            if (it) {
                Toast.makeText(
                    view.context,
                    getText(R.string.todo_added_toast),
                    Toast.LENGTH_SHORT
                ).show()
                todoListViewModel.switchOffAddedFlag()
            }
        })

        todoListViewModel.todoDeleted.observe(viewLifecycleOwner, Observer {
            if (it) {
                Toast.makeText(
                    view.context,
                    getText(R.string.todo_removed_toast),
                    Toast.LENGTH_SHORT
                ).show()
                todoListViewModel.switchOffDeletedFlag()
            }
        })
    }


    private fun initRecycler(view: View) {
        val onItemClick: (todo: Todo) -> Unit = { todo ->
            todo.id?.let {
                findNavController().navigate(TodoListFragmentDirections.getDetail(it))
            }
        }

        val onItemSwipeLeft: (todo: Todo) -> Unit = { todo ->
            todo.id?.let {
                todoListViewModel.deleteItem(it)
            }
        }

        adapter = MainAdapter(
            view.context,
            Collections.emptyList(),
            onItemClick,
            onItemSwipeLeft
        )
        recyclerView = view.findViewById<RecyclerView>(R.id.recycler)?.apply {
            adapter = this@TodoListFragment.adapter
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
