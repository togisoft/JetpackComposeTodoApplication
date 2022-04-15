package com.togitech.todo.feature_todo.data.repository

import com.togitech.todo.feature_todo.data.data_source.TodoDao
import com.togitech.todo.feature_todo.domain.model.Todo
import com.togitech.todo.feature_todo.domain.repository.TodoRepository
import kotlinx.coroutines.flow.Flow

class TodoRepositoryImpl(
    private val dao: TodoDao
) : TodoRepository {
    override fun getTodo(): Flow<List<Todo>> {
        return dao.getTodo()
    }

    override suspend fun getTodoById(id: Int): Todo? {
        return dao.getTodoById(id)
    }

    override suspend fun insertTodo(todo: Todo) {
        return dao.insertTodo(todo)
    }

    override suspend fun deleteTodo(todo: Todo) {
        return dao.deleteTodo(todo)
    }

    override suspend fun completedTodo(completed:Boolean,id:Int) {
        return dao.completedTodo(completed = completed, id = id)
    }
}