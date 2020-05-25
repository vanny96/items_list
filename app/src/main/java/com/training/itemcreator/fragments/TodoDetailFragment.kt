package com.training.itemcreator.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.google.android.material.textfield.TextInputEditText
import com.training.itemcreator.R
import com.training.itemcreator.model.Todo
import com.training.itemcreator.util.hideKeyboard
import com.training.itemcreator.viewmodel.TodoDetailViewModel
import com.training.itemcreator.viewmodel.factory.TodoViewModelFactory

class TodoDetailFragment : Fragment() {

    private lateinit var todoDetailViewModel: TodoDetailViewModel

    private val args: TodoDetailFragmentArgs by navArgs()

    lateinit var nameField: TextInputEditText
    lateinit var descriptionField: TextInputEditText
    lateinit var updateButton: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.todo_detail_fragment, container, false)

        initViewModel(view)

        nameField = view.findViewById(R.id.name_field)
        descriptionField = view.findViewById(R.id.description_field)
        updateButton = view.findViewById(R.id.update_button)

        nameField.onFocusChangeListener = onFocusChange
        descriptionField.onFocusChangeListener = onFocusChange
        updateButton.setOnClickListener(onUpdateClick)

        return view
    }

    private fun initViewModel(view: View) {
        todoDetailViewModel =
            ViewModelProvider(this, TodoViewModelFactory(view.context, args.itemId))
                .get(TodoDetailViewModel::class.java)

        todoDetailViewModel.getTodo().observe(viewLifecycleOwner, Observer {
            nameField.setText(it.name)
            descriptionField.setText(it.description)
        })

        todoDetailViewModel.todoUpdated.observe(viewLifecycleOwner, Observer {
            if (it) {
                Toast.makeText(
                    view.context,
                    getText(R.string.todo_updated_toast),
                    Toast.LENGTH_SHORT
                ).show()
                todoDetailViewModel.switchOffUpdatedFlag()
            }
        })
    }

    private val onUpdateClick = View.OnClickListener { v: View ->
        todoDetailViewModel.update(
            Todo(args.itemId, nameField.text.toString(), descriptionField.text.toString())
        )
    }

    private val onFocusChange = View.OnFocusChangeListener { v, hasFocus ->
        if (!hasFocus) hideKeyboard(v)
    }
}
