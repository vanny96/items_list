package com.training.itemcreator.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.training.itemcreator.model.Todo
import com.training.itemcreator.repository.TodoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TodoListViewModel(private val repository: TodoRepository) : ViewModel() {

    private val todoList: LiveData<List<Todo>> = repository.getList()
    val todoAdded = MutableLiveData<Boolean>()
    val todoDeleted = MutableLiveData<Boolean>()

    fun getTodos(): LiveData<List<Todo>> {
        return todoList
    }

    fun deleteItem(id: Int){
        viewModelScope.launch(Dispatchers.IO){
            repository.deleteItem(id)
            todoDeleted.postValue(true)
        }
    }

    fun addItem(name: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addItem(name)
            todoAdded.postValue(true)
        }
    }

    fun switchOffAddedFlag(){
        todoAdded.value = false
    }

    fun switchOffDeletedFlag(){
        todoDeleted.value = false
    }
}