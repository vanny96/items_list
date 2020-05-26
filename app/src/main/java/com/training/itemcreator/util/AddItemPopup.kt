package com.training.itemcreator.util

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import com.google.android.material.textfield.TextInputEditText
import com.training.itemcreator.R

object AddItemPopup {

    fun launchPopup(context: Context, onButtonPressed: (name: String) -> Unit) {
        val builder = AlertDialog.Builder(context).apply {
            setView(R.layout.dialog_popup_layout)
        }

        builder.show().apply {
            //Background invisible
            window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

            //Setups the edit field
            val inputText = findViewById<TextInputEditText>(R.id.new_todo_name_input)!!.apply {
                onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
                    if (!hasFocus) hideKeyboard(this) else
                        window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE)

                }
                requestFocus()
            }
            //Setups the 'add button'
            findViewById<Button>(R.id.add_button)?.setOnClickListener {
                onButtonPressed(inputText.text.toString())
                this.cancel()
            }
        }
    }
}