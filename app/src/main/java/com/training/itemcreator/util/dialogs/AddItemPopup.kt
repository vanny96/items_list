package com.training.itemcreator.util.dialogs

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.EditorInfo
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import com.google.android.material.textfield.TextInputEditText
import com.training.itemcreator.R
import com.training.itemcreator.util.KeyboardUtils

object AddItemPopup {
    fun launchPopup(context: Context, onButtonPressed: (name: String) -> Unit) {
        val builder = AlertDialog.Builder(context).apply {
            setView(R.layout.dialog_add_todo)
        }

        builder.show().apply {
            //Background invisible
            window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

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
                setOnEditorActionListener{_, action, _ ->
                    if(action == EditorInfo.IME_ACTION_DONE) {
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