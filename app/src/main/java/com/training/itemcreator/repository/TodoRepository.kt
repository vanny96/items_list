package com.training.itemcreator.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.sqlite.db.SimpleSQLiteQuery
import com.training.itemcreator.database.TodoDatabase
import com.training.itemcreator.model.Todo
import com.training.itemcreator.util.TodoSort
import com.training.itemcreator.util.enums.Priority

class TodoRepository(context: Context) {
    private val todoDao = TodoDatabase.getInstance(context).todoDao()
    var currentListLiveData : LiveData<List<Todo>>? = null
    private set

    fun addItem(todo: Todo) {
        todoDao.add(todo)
    }

    fun fetchTodos(sortOption: TodoSort, filterOptions: List<Priority>) : LiveData<List<Todo>> {
        val args = mutableListOf<String>()
        var query = "SELECT * FROM Todo"

        // Handle filter
        query = when(filterOptions.size){
            1 -> "$query WHERE priority IN (?)"
            2 -> "$query WHERE priority IN (?, ?)"
            3 -> "$query WHERE priority IN (?, ?, ?)"
            else -> query
        }
        args.addAll(filterOptions.map { it.ordinal.toString() })

        // Handle sort
        query = when(sortOption){
            TodoSort.PRIORITY -> "$query ORDER BY priority DESC"
            TodoSort.ID -> query
        }

        return todoDao.getList(SimpleSQLiteQuery(query, args.toTypedArray())).also {
            currentListLiveData = it
        }
    }

    fun getItem(itemId: Int): LiveData<Todo> {
        return todoDao.get(itemId)
    }

    fun update(updatedTodo: Todo) {
        todoDao.update(updatedTodo)
    }

    fun deleteItem(id: Int) {
        todoDao.delete(id)
    }
}