package com.training.itemcreator.util

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

fun hideKeyboard(v: View): Boolean{
    val inputManager = v.context?.getSystemService(Context.INPUT_METHOD_SERVICE)
            as InputMethodManager

    return inputManager.hideSoftInputFromWindow(
        v.windowToken,
        InputMethodManager.HIDE_NOT_ALWAYS
    )
}

fun showKeyboard(v: View) : Boolean{
    val inputManager = v.context?.getSystemService(Context.INPUT_METHOD_SERVICE)
            as InputMethodManager

    return inputManager.showSoftInput(v, InputMethodManager.SHOW_IMPLICIT)
}