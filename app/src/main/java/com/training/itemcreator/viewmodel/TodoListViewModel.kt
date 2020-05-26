package com.training.itemcreator.viewmodel

import androidx.lifecycle.*
import com.training.itemcreator.model.Todo
import com.training.itemcreator.repository.TodoRepository
import com.training.itemcreator.util.TodoSorts
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TodoListViewModel(private val repository: TodoRepository) : ViewModel() {

    private var sortFunction = TodoSorts.priorityOrder
    val todoAdded = MutableLiveData<Boolean>()
    val todoDeleted = MutableLiveData<Boolean>()

    private val todoList = MediatorLiveData<List<Todo>>().apply {
        addSource(repository.getList()) {
            this.value = it.also { sort() }
        }
    }

    fun getTodos(): LiveData<List<Todo>> {
        return todoList
    }

    fun deleteItem(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
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

    fun setSortFunction(func: (Todo) -> Int) {
        sortFunction = func
        sort()
    }

    fun sort(){
        viewModelScope.launch(Dispatchers.IO) {
            todoList.postValue(todoList.value?.sortedBy(sortFunction))
        }
    }

    fun switchOffAddedFlag() {
        todoAdded.value = false
    }

    fun switchOffDeletedFlag() {
        todoDeleted.value = false
    }
}