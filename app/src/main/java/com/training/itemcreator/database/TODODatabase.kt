package com.training.itemcreator.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.training.itemcreator.dao.TodoDao
import com.training.itemcreator.model.Todo

@Database(entities = arrayOf(Todo::class), version = 1)
abstract class TODODatabase : RoomDatabase() {
    abstract fun todoDao() : TodoDao

    companion object{
        @Volatile
        private var database : TODODatabase? = null

        fun getInstance(context: Context) : TODODatabase {
            return database ?: synchronized(this) {
                database ?: Room.databaseBuilder(context, TODODatabase::class.java, "todo-db")
                    .allowMainThreadQueries()
                    .build().also { database = it }
            }
        }
    }
}