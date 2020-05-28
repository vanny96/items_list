package com.training.itemcreator.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteQuery
import com.training.itemcreator.model.Todo

@Dao
interface TodoDao {

    @Insert
    fun add(todo: Todo)

    @RawQuery(observedEntities = [Todo::class])
    fun getList(query: SupportSQLiteQuery): LiveData<List<Todo>>

    @Query("SELECT * FROM Todo where id = (:id)")
    fun get(id: Int): LiveData<Todo>

    @Query("DELETE FROM Todo where id = (:id)")
    fun delete(id: Int)

    @Update
    fun update(todo: Todo)
}