package com.training.itemcreator.util.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.widget.SwitchCompat
import androidx.fragment.app.DialogFragment
import com.training.itemcreator.R
import com.training.itemcreator.util.TodoFilterTracker

class TodoFilterDialogFragment(
    private val todoFilterTracker: TodoFilterTracker,
    private val onUpdateFilters: (TodoFilterTracker) -> Unit
) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(activity).apply {
            setView(R.layout.dialog_filter_todo)
            setTitle(R.string.filter_label)
        }.create().also {
            it.setOnShowListener { _ ->
                val lowPriorityButton = it.findViewById<SwitchCompat>(R.id.filter_low_switch)
                val midPriorityButton = it.findViewById<SwitchCompat>(R.id.filter_mid_switch)
                val highPriorityButton = it.findViewById<SwitchCompat>(R.id.filter_high_switch)

                val updateFiltersButton = it.findViewById<Button>(R.id.update_filters_button)

                lowPriorityButton.isChecked = todoFilterTracker.lowAllowed
                midPriorityButton.isChecked = todoFilterTracker.midAllowed
                highPriorityButton.isChecked = todoFilterTracker.highAllowed

                updateFiltersButton.setOnClickListener {_ ->
                    onUpdateFilters(
                        TodoFilterTracker(
                            lowPriorityButton.isChecked,
                            midPriorityButton.isChecked,
                            highPriorityButton.isChecked
                        )
                    )
                    it.dismiss()
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        return super.onCreateView(inflater, container, savedInstanceState)
    }
}