package com.training.itemcreator.util.dialogs

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.SwitchCompat
import androidx.fragment.app.DialogFragment
import com.training.itemcreator.R
import com.training.itemcreator.fragments.TodoListFragment
import com.training.itemcreator.util.TodoFilterTracker
import com.training.itemcreator.viewmodel.TodoListViewModel

class TodoFilterDialogFragment() : DialogFragment() {

    private lateinit var todoListViewModel: TodoListViewModel

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(requireContext()).apply {
            setView(R.layout.dialog_filter_todo)
        }.create().apply {
            this.setOnShowListener { _ ->
                val lowPriorityButton = this.findViewById<SwitchCompat>(R.id.filter_low_switch)
                val midPriorityButton = this.findViewById<SwitchCompat>(R.id.filter_mid_switch)
                val highPriorityButton = this.findViewById<SwitchCompat>(R.id.filter_high_switch)

                val updateFiltersButton = this.findViewById<Button>(R.id.update_filters_button)

                lowPriorityButton?.isChecked = todoListViewModel.todoFilterUtil.lowAllowed
                midPriorityButton?.isChecked = todoListViewModel.todoFilterUtil.midAllowed
                highPriorityButton?.isChecked = todoListViewModel.todoFilterUtil.highAllowed

                updateFiltersButton?.setOnClickListener { _ ->
                    todoListViewModel.todoFilterUtil = TodoFilterTracker(
                        lowPriorityButton?.isChecked ?: false,
                        midPriorityButton?.isChecked ?: false,
                        highPriorityButton?.isChecked ?: false
                    )
                    this.dismiss()
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
        todoListViewModel = (parentFragment as TodoListFragment).todoListViewModel

        return super.onCreateView(inflater, container, savedInstanceState)
    }
}