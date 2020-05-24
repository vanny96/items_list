package com.training.itemcreator.dao

import com.training.itemcreator.model.TodoModel

object TodoDao {
    val data = mutableListOf<TodoModel>()

    fun add(todoModel: TodoModel){
        data.add(todoModel)
    }

    fun getList(): List<TodoModel>{
        return data.toList()
    }

    fun get(id: Int): TodoModel{
        return data[id]
    }

    fun delete(id: Int){
        data.removeAt(id)
    }

    fun update(id: Int, todoModel: TodoModel){
        data[id] = todoModel
    }
}