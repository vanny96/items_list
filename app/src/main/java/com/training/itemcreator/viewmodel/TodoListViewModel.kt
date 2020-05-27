package com.training.itemcreator.viewmodel

import androidx.lifecycle.*
import com.training.itemcreator.model.Todo
import com.training.itemcreator.repository.TodoRepository
import com.training.itemcreator.util.TodoFilterTracker
import com.training.itemcreator.util.TodoSorts
import com.training.itemcreator.util.enums.Priority
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TodoListViewModel(private val repository: TodoRepository) : ViewModel() {

    private val originalTodoList = repository.getList()
    private val todoList = MediatorLiveData<List<Todo>>().apply {
        addSource(originalTodoList) {
            this.value = it
            viewModelScope.launch(Dispatchers.IO) {
                val filteredList = filter(it)
                val sortedList = sort(filteredList)
                this@apply.postValue(sortedList)
            }
        }
    }

    val todoAdded = MutableLiveData<Boolean>()
    val todoDeleted = MutableLiveData<Boolean>()

    var sortFunction = TodoSorts.naturalOrder
        set(value) {
            field = value
            viewModelScope.launch(Dispatchers.IO) {
                todoList.postValue(sort(todoList.value))
            }
        }

    var todoFilterUtil = TodoFilterTracker()
        set(value) {
            field = value
            viewModelScope.launch(Dispatchers.IO) {
                val filteredList = filter(originalTodoList.value)
                val sortedList = sort(filteredList)
                todoList.postValue(sortedList)
            }
        }

    // Operations
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

    // Notify if operation completed
    fun switchOffAddedFlag() {
        todoAdded.value = false
    }

    fun switchOffDeletedFlag() {
        todoDeleted.value = false
    }

    // Filtering and sorting
    private fun sort(list: List<Todo>?) : List<Todo>? {
        return list?.sortedBy(sortFunction)
    }

    private fun filter(list : List<Todo>?) : List<Todo>? {
        return list?.filter {
            when (it.priority) {
                Priority.LOW -> todoFilterUtil.lowAllowed
                Priority.MEDIUM -> todoFilterUtil.midAllowed
                Priority.HIGH -> todoFilterUtil.highAllowed
            }
        }
    }
}
