package com.training.itemcreator.repository

import com.training.itemcreator.dao.TodoDao
import com.training.itemcreator.model.TodoModel

class TodoRepository {

    fun getList() : List<TodoModel>{
        return TodoDao.getList()
    }

    fun addItem(name: String){
        TodoDao.add(TodoModel(TodoDao.getList().size, name, null))
    }

    fun getItem(id: Int) : TodoModel{
        return TodoDao.get(id)
    }

    fun update(id: Int, updatedTodo: TodoModel) {
        TodoDao.update(id, updatedTodo)
    }
}