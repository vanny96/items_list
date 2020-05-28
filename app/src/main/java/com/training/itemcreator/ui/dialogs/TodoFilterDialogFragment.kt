package com.training.itemcreator.ui.dialogs

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
import com.training.itemcreator.util.enums.Priority
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

                lowPriorityButton?.isChecked = Priority.LOW in todoListViewModel.filterOptions
                midPriorityButton?.isChecked = Priority.MEDIUM in todoListViewModel.filterOptions
                highPriorityButton?.isChecked = Priority.HIGH in todoListViewModel.filterOptions

                updateFiltersButton?.setOnClickListener { _ ->
                    todoListViewModel.filterOptions = mutableListOf<Priority>().apply {
                        if(lowPriorityButton?.isChecked == true) add(Priority.LOW)
                        if(midPriorityButton?.isChecked == true) add(Priority.MEDIUM)
                        if(highPriorityButton?.isChecked == true) add(Priority.HIGH)
                    }
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