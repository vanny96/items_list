package com.training.itemcreator.viewmodel.factory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.training.itemcreator.repository.TodoRepository
import com.training.itemcreator.viewmodel.TodoDetailViewModel
import com.training.itemcreator.viewmodel.TodoListViewModel

class TodoViewModelFactory(private val context: Context, private val todoId: Int = 0) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(TodoListViewModel::class.java)) {
            TodoListViewModel(TodoRepository(context)) as T
        } else if(modelClass.isAssignableFrom(TodoDetailViewModel::class.java)){
            TodoDetailViewModel(TodoRepository(context), todoId) as T
        }else {
            super.create(modelClass)
        }
    }
}