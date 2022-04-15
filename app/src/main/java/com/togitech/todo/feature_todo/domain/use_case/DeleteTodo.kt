package com.togitech.todo.feature_todo.domain.use_case

import com.togitech.todo.feature_todo.domain.model.Todo
import com.togitech.todo.feature_todo.domain.repository.TodoRepository

class DeleteTodo(private val repository: TodoRepository) {
    suspend operator fun invoke(todo: Todo) {
        repository.deleteTodo(todo)
    }
}