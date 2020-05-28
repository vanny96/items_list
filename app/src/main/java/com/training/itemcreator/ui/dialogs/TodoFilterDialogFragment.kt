package com.training.itemcreator.ui.dialogs

import android.app.Dialog
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CompoundButton
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

    private var lowPriorityButton: SwitchCompat? = null
    private var midPriorityButton: SwitchCompat? = null
    private var highPriorityButton: SwitchCompat? = null

    private var updateFiltersButton: Button? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(requireContext()).apply {
            setView(R.layout.dialog_filter_todo)
        }.create().apply {
            setOnShowListener { _ ->
                lowPriorityButton = this.findViewById<SwitchCompat>(R.id.filter_low_switch)
                midPriorityButton = this.findViewById<SwitchCompat>(R.id.filter_mid_switch)
                highPriorityButton = this.findViewById<SwitchCompat>(R.id.filter_high_switch)

                updateFiltersButton = this.findViewById<Button>(R.id.update_filters_button)

                setupRadioButtons()

                updateFiltersButton?.setOnClickListener { _ ->
                    todoListViewModel.tempFilterOptions?.let {
                        todoListViewModel.filterOptions = it
                    }
                    todoListViewModel.tempFilterOptions = null
                    this.dismiss()
                }
            }
        }
    }

    override fun onCancel(dialog: DialogInterface) {
        todoListViewModel.tempFilterOptions = null
        super.onCancel(dialog)
    }

    fun setupRadioButtons() {
        todoListViewModel.tempFilterOptions?.let {
            lowPriorityButton?.isChecked = Priority.LOW in it
            midPriorityButton?.isChecked = Priority.MEDIUM in it
            highPriorityButton?.isChecked = Priority.HIGH in it
        } ?: run {
            lowPriorityButton?.isChecked = Priority.LOW in todoListViewModel.filterOptions
            midPriorityButton?.isChecked = Priority.MEDIUM in todoListViewModel.filterOptions
            highPriorityButton?.isChecked = Priority.HIGH in todoListViewModel.filterOptions

            todoListViewModel.tempFilterOptions = todoListViewModel.filterOptions.toMutableList()
        }

        lowPriorityButton?.setOnCheckedChangeListener(onRadioChange(Priority.LOW))
        midPriorityButton?.setOnCheckedChangeListener(onRadioChange(Priority.MEDIUM))
        highPriorityButton?.setOnCheckedChangeListener(onRadioChange(Priority.HIGH))
    }

    private val onRadioChange = { priority: Priority ->
        CompoundButton.OnCheckedChangeListener { _, isChecked ->
            todoListViewModel.tempFilterOptions?.apply {
                if (isChecked) add(priority) else remove(priority)
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