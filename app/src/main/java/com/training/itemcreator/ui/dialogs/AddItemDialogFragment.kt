package com.training.itemcreator.ui.dialogs

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.RadioGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.google.android.material.textfield.TextInputEditText
import com.training.itemcreator.R
import com.training.itemcreator.fragments.TodoListFragment
import com.training.itemcreator.model.Todo
import com.training.itemcreator.util.KeyboardUtils
import com.training.itemcreator.util.enums.Priority

class AddItemDialogFragment : DialogFragment() {

    private lateinit var onButtonPressed: (Todo) -> Unit

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(requireContext()).apply {
            setView(R.layout.dialog_add_todo)
        }.create().apply {
            setOnShowListener {

                //Setups the edit field and add button
                val button: Button? = findViewById(R.id.add_button)
                val nameInput: TextInputEditText? = findViewById(R.id.new_todo_name_input)
                val descriptionInput: TextInputEditText? =
                    findViewById(R.id.new_todo_description_input)
                val priorityGroup: RadioGroup? = findViewById(R.id.PriorityGroup)

                button?.setOnClickListener {
                    if(TextUtils.isEmpty(nameInput?.text?.trim())){
                        nameInput?.error = getText(R.string.name_required_error)
                    } else {
                        onButtonPressed(Todo(
                            null,
                            nameInput?.text.toString(),
                            descriptionInput?.text.toString(),
                            priorityGroup?.checkedRadioButtonId?.let { it -> Priority.fromId(it) }
                                ?: Priority.LOW
                        ))
                        this.cancel()
                    }
                }

                nameInput?.apply {
                    onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
                        if (!hasFocus) KeyboardUtils.hideKeyboard(this)
                    }
                }

                descriptionInput?.apply {
                    onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
                        if (!hasFocus) KeyboardUtils.hideKeyboard(this)
                    }
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        onButtonPressed = (parentFragment as TodoListFragment).onAddItem
        return view
    }
}