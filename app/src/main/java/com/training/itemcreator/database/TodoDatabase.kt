package com.training.itemcreator.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.training.itemcreator.dao.TodoDao
import com.training.itemcreator.model.Todo
import com.training.itemcreator.util.enums.Priority

@Database(entities = [Todo::class], version = 2)
@TypeConverters(Priority.TypeConverterUtil::class)
abstract class TodoDatabase : RoomDatabase() {
    abstract fun todoDao() : TodoDao

    companion object{
        @Volatile
        private var database : TodoDatabase? = null

        val MIGRATION_1_2 = object : Migration(1,2){
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE Todo ADD priority INTEGER NOT NULL DEFAULT 0")
            }
        }

        fun getInstance(context: Context) : TodoDatabase {
            return database ?: synchronized(this) {
                database ?: Room.databaseBuilder(context, TodoDatabase::class.java, "todo-db")
                    .addMigrations(MIGRATION_1_2)
                    .build().also { database = it }
            }
        }
    }
}