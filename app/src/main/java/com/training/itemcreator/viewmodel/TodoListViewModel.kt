package com.training.itemcreator.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.training.itemcreator.model.Todo
import com.training.itemcreator.repository.TodoRepository
import kotlinx.coroutines.launch

class TodoListViewModel(private var repository: TodoRepository) : ViewModel() {
    private var todoList: LiveData<List<Todo>> = repository.getList()

    fun getTodos() : LiveData<List<Todo>>{
        return todoList
    }

    fun addItem(name: String){
        viewModelScope.launch {
            repository.addItem(name)
        }
    }
}