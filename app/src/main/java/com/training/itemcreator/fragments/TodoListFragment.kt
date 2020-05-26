package com.training.itemcreator.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.training.itemcreator.R
import com.training.itemcreator.adapters.TodoListRecyclerAdapter
import com.training.itemcreator.model.Todo
import com.training.itemcreator.util.AddItemPopup
import com.training.itemcreator.util.TodoItemTouchHelper
import com.training.itemcreator.viewmodel.TodoListViewModel
import com.training.itemcreator.viewmodel.factory.TodoViewModelFactory

class TodoListFragment : Fragment() {

    private lateinit var todoListViewModel: TodoListViewModel

    private lateinit var adapter: TodoListRecyclerAdapter
    private lateinit var recyclerView: RecyclerView

    private lateinit var actionButton: FloatingActionButton

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.todo_list_fragment, container, false)

        initRecycler(view)
        initViewModel(view)

        actionButton = view.findViewById(R.id.floatingActionButton)
        actionButton.setOnClickListener{
            AddItemPopup.launchPopup(view.context, onAddItem)
        }

        return view;
    }

    private fun initRecycler(view: View) {
        val onItemClick: (Todo) -> Unit = { todo ->
            todo.id?.let {
                findNavController().navigate(TodoListFragmentDirections.getDetail(it))
            }
        }

        val onItemSwipeLeft: (Todo) -> Unit = { todo ->
            todo.id?.let {
                todoListViewModel.deleteItem(it)
            }
        }

        adapter = TodoListRecyclerAdapter(
            view.context,
            onItemClick,
            onItemSwipeLeft
        )
        recyclerView = view.findViewById<RecyclerView>(R.id.recycler).apply {
            adapter = this@TodoListFragment.adapter
            layoutManager = LinearLayoutManager(context)
        }

        ItemTouchHelper(TodoItemTouchHelper(adapter)).attachToRecyclerView(recyclerView)
    }

    private fun initViewModel(view: View) {
        todoListViewModel = ViewModelProvider(this, TodoViewModelFactory(view.context))
            .get(TodoListViewModel::class.java)

        todoListViewModel.getTodos().observe(viewLifecycleOwner, Observer {
            adapter.data = it
            adapter.notifyDataSetChanged()
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

    private val onAddItem = { name: String ->
        todoListViewModel.addItem(name)
    }
}
