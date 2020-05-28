package com.training.itemcreator.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RadioGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.textfield.TextInputEditText
import com.training.itemcreator.R
import com.training.itemcreator.model.Todo
import com.training.itemcreator.util.KeyboardUtils
import com.training.itemcreator.util.enums.Priority
import com.training.itemcreator.viewmodel.TodoDetailViewModel
import com.training.itemcreator.viewmodel.factory.TodoViewModelFactory

class TodoDetailFragment : Fragment() {

    private lateinit var todoDetailViewModel: TodoDetailViewModel

    private val args: TodoDetailFragmentArgs by navArgs()

    lateinit var nameField: TextInputEditText
    lateinit var descriptionField: TextInputEditText
    lateinit var updateButton: Button
    lateinit var priorityGroup: RadioGroup

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.todo_detail_fragment, container, false)

        nameField = view.findViewById(R.id.name_field)
        descriptionField = view.findViewById(R.id.description_field)
        priorityGroup = view.findViewById(R.id.PriorityGroup)
        updateButton = view.findViewById(R.id.update_button)

        initViewModel(view)

        nameField.apply {
            onFocusChangeListener = onFocusChange
            doAfterTextChanged {
                todoDetailViewModel.editedTodo?.name = nameField.text.toString()
                checkIfEdited()
            }
        }

        descriptionField.apply {
            onFocusChangeListener = onFocusChange
            doAfterTextChanged {
                todoDetailViewModel.editedTodo?.description = descriptionField.text.toString()
                checkIfEdited()
            }
        }

        priorityGroup.apply {
            setOnCheckedChangeListener { _, _ ->
                todoDetailViewModel.editedTodo?.priority =
                    Priority.fromId(priorityGroup.checkedRadioButtonId)
                checkIfEdited()
            }
        }

        updateButton.setOnClickListener(onUpdateClick(findNavController()))

        return view
    }

    private fun checkIfEdited() {
        if (todoDetailViewModel.editedTodo?.equals(todoDetailViewModel.todo.value) == true) {
            todoDetailViewModel.edited = false
            updateButton.visibility = View.GONE
        } else {
            todoDetailViewModel.edited = true
            updateButton.visibility = View.VISIBLE
        }
    }

    private fun initViewModel(view: View) {
        todoDetailViewModel =
            ViewModelProvider(this, TodoViewModelFactory(view.context, args.itemId))
                .get(TodoDetailViewModel::class.java)

        // If the fragment is destroyed and recreated, this will make sure that the data is preserved
        todoDetailViewModel.editedTodo?.let {
            nameField.setText(it.name)
            descriptionField.setText(it.description)
            priorityGroup.check(it.priority.id)
        }

        todoDetailViewModel.todo.observe(viewLifecycleOwner, Observer {
            todoDetailViewModel.editedTodo =
                todoDetailViewModel.editedTodo ?: Todo(it.id, it.name, it.description, it.priority)

            if (!todoDetailViewModel.edited) {
                nameField.setText(it.name)
                descriptionField.setText(it.description)
                priorityGroup.check(it.priority.id)
            }
        })
    }

    private val onUpdateClick = { nav: NavController ->
        View.OnClickListener {
            todoDetailViewModel.update(
                Todo(
                    args.itemId,
                    nameField.text.toString(),
                    descriptionField.text.toString(),
                    Priority.fromId(priorityGroup.checkedRadioButtonId)
                )
            )
            nav.popBackStack()
        }
    }

    private val onFocusChange = View.OnFocusChangeListener { v, hasFocus ->
        if (!hasFocus) KeyboardUtils.hideKeyboard(v)
    }
}
