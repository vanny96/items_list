package com.training.itemcreator.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.navigation.fragment.navArgs
import com.google.android.material.textfield.TextInputEditText
import com.training.itemcreator.R
import com.training.itemcreator.model.TodoModel
import com.training.itemcreator.repository.TodoRepository
import com.training.itemcreator.util.hideKeyboard

class DetailPage : Fragment() {

    private val repository = TodoRepository()

    private val todo: TodoModel by lazy {
        val args: DetailPageArgs by navArgs()
        repository.getItem(args.itemId)
    }

    lateinit var nameField: TextInputEditText
    lateinit var descriptionField: TextInputEditText
    lateinit var updateButton: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_detail_page, container, false)

        nameField = view.findViewById(R.id.name_field)
        descriptionField = view.findViewById(R.id.description_field)
        updateButton = view.findViewById(R.id.update_button)

        nameField.setText(todo.name)
        descriptionField.setText(todo.description)

        nameField.onFocusChangeListener = onFocusChange
        descriptionField.onFocusChangeListener = onFocusChange
        updateButton.setOnClickListener(onUpdateClick)

        return view
    }

    private val onUpdateClick = View.OnClickListener{ v : View ->
        todo.id?.let { id ->
            val updatedTodo = TodoModel(id, nameField.text.toString(), descriptionField.text.toString())
            repository.update(id, updatedTodo)
            Toast.makeText(v.context, getText(R.string.todo_updated_toast), Toast.LENGTH_SHORT).show()
        }
    }

    private val onFocusChange = View.OnFocusChangeListener{ v, hasFocus ->
        if(!hasFocus) hideKeyboard(v)
    }
}
