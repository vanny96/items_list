package com.training.itemcreator.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.training.itemcreator.model.Todo

@Dao
interface TodoDao {

    @Insert
    fun add(todo: Todo)

    @Query("SELECT * FROM Todo")
    fun getList(): LiveData<List<Todo>>

    @Query("SELECT * FROM Todo where id = (:id)")
    fun get(id: Int): LiveData<Todo>

    @Query("DELETE FROM Todo where id = (:id)")
    fun delete(id: Int)

    @Update
    fun update(todo: Todo)
}