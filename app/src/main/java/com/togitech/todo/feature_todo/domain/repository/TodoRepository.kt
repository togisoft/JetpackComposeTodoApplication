package com.togitech.todo.feature_todo.domain.repository

import com.togitech.todo.feature_todo.domain.model.Todo
import kotlinx.coroutines.flow.Flow

interface TodoRepository {
    fun getTodo(): Flow<List<Todo>>

    suspend fun getTodoById(id: Int): Todo?

    suspend fun insertTodo(todo: Todo)

    suspend fun deleteTodo(todo: Todo)

    suspend fun completedTodo(completed:Boolean,id: Int)
}