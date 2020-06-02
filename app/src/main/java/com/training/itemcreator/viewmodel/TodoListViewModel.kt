package com.training.itemcreator.viewmodel

import androidx.lifecycle.*
import com.training.itemcreator.model.Todo
import com.training.itemcreator.repository.TodoRepository
import com.training.itemcreator.util.TodoSort
import com.training.itemcreator.util.enums.Priority
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TodoListViewModel(private val repository: TodoRepository) : ViewModel() {

    // Sort option selected from SORT menu
    var sortOption = TodoSort.PRIORITY
        set(value) {
            field = value
            refreshData()
        }

    // Filter options selected from FILTER menu
    var filterOptions = listOf(Priority.LOW, Priority.MEDIUM, Priority.HIGH)
        set(value) {
            field = value
            refreshData()
        }
    // Filter to use when recreating filter dialog in Destruction -> Create
    var tempFilterOptions: MutableList<Priority>? = null

    // LiveData source used by the view, it changes sources once the filter or the sortoptions change
    val todoList = MediatorLiveData<List<Todo>>().apply {
        addSource(repository.fetchTodos(sortOption, filterOptions)) {
            this.value = it
        }
    }

    // Flags used to notify when an operation is completed
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

    fun addItem(todo: Todo) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addItem(todo)
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
