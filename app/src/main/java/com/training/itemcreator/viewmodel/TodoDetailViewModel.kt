package com.training.itemcreator.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.training.itemcreator.model.Todo
import com.training.itemcreator.repository.TodoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TodoDetailViewModel(private val repository: TodoRepository, todoId: Int) : ViewModel() {
    val todo: LiveData<Todo> = repository.getItem(todoId)
    var editedTodo: Todo? = null
    var edited = false

    fun update(todo: Todo){
        viewModelScope.launch(Dispatchers.IO) {
            repository.update(todo)
        }
    }
}