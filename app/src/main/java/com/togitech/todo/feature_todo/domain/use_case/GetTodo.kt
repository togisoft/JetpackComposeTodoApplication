package com.togitech.todo.feature_todo.domain.use_case

import com.togitech.todo.feature_todo.domain.model.Todo
import com.togitech.todo.feature_todo.domain.repository.TodoRepository

class GetTodo(private val repository: TodoRepository) {
    suspend operator fun invoke(id: Int): Todo? {
        return repository.getTodoById(id)
    }
}