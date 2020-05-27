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
import com.training.itemcreator.util.enums.Priority
import com.training.itemcreator.util.hideKeyboard
import com.training.itemcreator.viewmodel.TodoDetailViewModel
import com.training.itemcreator.viewmodel.factory.TodoViewModelFactory

class TodoDetailFragment : Fragment() {

    private lateinit var todoDetailViewModel: TodoDetailViewModel

    private val args: TodoDetailFragmentArgs by navArgs()

    var edited = false
    lateinit var todoReference: Todo

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
            doAfterTextChanged { checkIfEdited() }
        }

        descriptionField.apply {
            onFocusChangeListener = onFocusChange
            doAfterTextChanged { checkIfEdited() }
        }

        priorityGroup.apply {
            setOnCheckedChangeListener { _, _ -> checkIfEdited() }
        }

        updateButton.setOnClickListener(onUpdateClick(findNavController()))

        return view
    }

    private fun checkIfEdited() {
        if (descriptionField.text.toString() != todoReference.description ||
            nameField.text.toString() != todoReference.name ||
            Priority.fromId(priorityGroup.checkedRadioButtonId) != todoReference.priority
        ) {
            edited = true
            updateButton.visibility = View.VISIBLE
        } else {
            edited = false
            updateButton.visibility = View.GONE
        }
    }

    private fun initViewModel(view: View) {
        todoDetailViewModel =
            ViewModelProvider(this, TodoViewModelFactory(view.context, args.itemId))
                .get(TodoDetailViewModel::class.java)

        todoDetailViewModel.getTodo().observe(viewLifecycleOwner, Observer {
            todoReference = it

            if (!edited) {
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
        if (!hasFocus) hideKeyboard(v)
    }
}
