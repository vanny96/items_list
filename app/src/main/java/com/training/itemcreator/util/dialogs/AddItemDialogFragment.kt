package com.training.itemcreator.util.dialogs

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.inputmethod.EditorInfo
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.google.android.material.textfield.TextInputEditText
import com.training.itemcreator.R
import com.training.itemcreator.fragments.TodoListFragment
import com.training.itemcreator.util.KeyboardUtils

class AddItemDialogFragment : DialogFragment() {

    private lateinit var onButtonPressed: (String) -> Unit

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(requireContext()).apply {
            setView(R.layout.dialog_add_todo)
        }.create().apply {
            setOnShowListener {

                //Setups the edit field and add button
                val button: Button? = findViewById(R.id.add_button)
                val inputText: TextInputEditText? = findViewById(R.id.new_todo_name_input)

                button?.setOnClickListener {
                    onButtonPressed(inputText?.text.toString())
                    this.cancel()
                }

                inputText?.apply {
                    onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
                        if (!hasFocus) KeyboardUtils.hideKeyboard(
                            this
                        ) else
                            window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE)

                    }
                    setOnEditorActionListener { _, action, _ ->
                        if (action == EditorInfo.IME_ACTION_DONE) {
                            button?.performClick()
                            return@setOnEditorActionListener true
                        } else {
                            return@setOnEditorActionListener false
                        }
                    }
                    requestFocus()
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