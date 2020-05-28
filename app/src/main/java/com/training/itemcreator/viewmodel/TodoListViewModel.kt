package com.training.itemcreator.viewmodel

import androidx.lifecycle.*
import com.training.itemcreator.model.Todo
import com.training.itemcreator.repository.TodoRepository
import com.training.itemcreator.util.TodoSort
import com.training.itemcreator.util.enums.Priority
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TodoListViewModel(private val repository: TodoRepository) : ViewModel() {

    var sortOption = TodoSort.PRIORITY
        set(value) {
            field = value
            refreshData()
        }
    var filterOptions = listOf(Priority.LOW, Priority.MEDIUM, Priority.HIGH)
        set(value) {
            field = value
            refreshData()
        }

    val todoList = MediatorLiveData<List<Todo>>().apply {
        addSource(repository.fetchTodos(sortOption, filterOptions)) {
            this.value = it
        }
    }

    val todoAdded = MutableLiveData<Boolean>()
    val todoDeleted = MutableLiveData<Boolean>()

    // Operations
    fun refreshData() {
        todoList.apply {
            repository.currentListLiveData?.let { removeSource(it) }
            addSource(repository.fetchTodos(sortOption, filterOptions)) {
                this.value = it
            }
        }
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

    // Notify if operation completed
    fun switchOffAddedFlag() {
        todoAdded.value = false
    }

    fun switchOffDeletedFlag() {
        todoDeleted.value = false
    }
}
