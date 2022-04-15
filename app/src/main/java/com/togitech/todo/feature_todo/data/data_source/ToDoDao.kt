package com.togitech.todo.feature_todo.data.data_source

import androidx.room.*
import com.togitech.todo.feature_todo.domain.model.Todo
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoDao {
    @Query("SELECT * FROM todo")
    fun getTodo(): Flow<List<Todo>>

    @Query("SELECT * FROM todo WHERE id= :id")
    suspend fun getTodoById(id: Int): Todo?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTodo(todo: Todo)

    @Delete
    suspend fun deleteTodo(todo: Todo)

    @Query("UPDATE todo SET isComplete = :completed WHERE id= :id")
    suspend fun completedTodo(completed:Boolean,id:Int)
}