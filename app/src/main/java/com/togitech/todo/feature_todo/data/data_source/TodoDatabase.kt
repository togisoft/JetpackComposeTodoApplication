package com.togitech.todo.feature_todo.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import com.togitech.todo.feature_todo.domain.model.Todo

@Database(
    entities = [Todo::class], version = 1
)

abstract class TodoDatabase : RoomDatabase() {
    abstract val toDoDao: TodoDao

    companion object {
        const val DATABASE_NAME = "notes_db"
    }
}