package com.training.itemcreator.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.training.itemcreator.model.Todo
import com.training.itemcreator.repository.TodoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TodoDetailViewModel(private val repository: TodoRepository, private val todoId: Int) : ViewModel() {
    private val todo: LiveData<Todo> = repository.getItem(todoId)

    fun getTodo() : LiveData<Todo>{
        return todo
    }

    fun update(todo: Todo){
        viewModelScope.launch(Dispatchers.IO) {
            repository.update(todo)
        }
    }
}