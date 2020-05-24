package com.training.itemcreator.repository

import android.content.Context
import com.training.itemcreator.database.TODODatabase
import com.training.itemcreator.model.Todo

class TodoRepository(context: Context) {
    private val todoDao = TODODatabase.getInstance(context).todoDao()

    fun addItem(name: String) {
        todoDao.add(Todo(null, name, null))
    }

    fun getList(): List<Todo> {
        return todoDao.getList()
    }

    fun getItem(itemId: Int) : Todo {
        return todoDao.get(itemId)
    }

    fun update(updatedTodo: Todo) {
        todoDao.update(updatedTodo)
    }


}