package com.training.itemcreator.repository

import android.app.Application
import android.content.Context
import androidx.lifecycle.LiveData
import com.training.itemcreator.database.TodoDatabase
import com.training.itemcreator.model.Todo

class TodoRepository(context: Context) {
    private val todoDao = TodoDatabase.getInstance(context).todoDao()

    fun addItem(name: String) {
        todoDao.add(Todo(null, name, null))
    }

    fun getList(): LiveData<List<Todo>> {
        return todoDao.getList()
    }

    fun getItem(itemId: Int) : LiveData<Todo> {
        return todoDao.get(itemId)
    }

    fun update(updatedTodo: Todo) {
        todoDao.update(updatedTodo)
    }

    fun deleteItem(id: Int) {
        todoDao.delete(id)
    }
}