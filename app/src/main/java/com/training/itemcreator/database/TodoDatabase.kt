package com.training.itemcreator.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.training.itemcreator.dao.TodoDao
import com.training.itemcreator.model.Todo

@Database(entities = arrayOf(Todo::class), version = 1)
abstract class TodoDatabase : RoomDatabase() {
    abstract fun todoDao() : TodoDao

    companion object{
        @Volatile
        private var database : TodoDatabase? = null

        fun getInstance(context: Context) : TodoDatabase {
            return database ?: synchronized(this) {
                database ?: Room.databaseBuilder(context, TodoDatabase::class.java, "todo-db")
                    .build().also { database = it }
            }
        }
    }
}