package com.training.itemcreator.ui.snackbars

import android.view.View
import com.google.android.material.snackbar.Snackbar
import com.training.itemcreator.R

class DeleteTodoSnackbar(
    view: View,
    private val onNotDismissed: () -> Unit,
    private val onDismissed: () -> Unit
) {

    private val getText: (Int) -> CharSequence = view.context.resources::getText

    private val callback = object : Snackbar.Callback() {
        override fun onDismissed(transientBottomBar: Snackbar?, event: Int) {
            if (event == DISMISS_EVENT_ACTION) onDismissed()
            else onNotDismissed()
            super.onDismissed(transientBottomBar, event)
        }
    }

    private val snackbar: Snackbar =
        Snackbar.make(view, getText(R.string.todo_removed_toast), Snackbar.LENGTH_LONG)
            .apply {
                animationMode = Snackbar.ANIMATION_MODE_SLIDE

                setAction(getText(R.string.todo_undo_removed)) {}

                addCallback(callback)
            }

    fun show() {
        snackbar.show()
    }
}